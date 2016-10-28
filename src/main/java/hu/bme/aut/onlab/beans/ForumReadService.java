package hu.bme.aut.onlab.beans;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberLike;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Post_;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Subcategory_;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.model.TopicSeenByMember;
import hu.bme.aut.onlab.model.TopicSeenByMember_;
import hu.bme.aut.onlab.model.Topic_;

@LocalBean
@Stateless
public class ForumReadService {

	@PersistenceContext
	private EntityManager em;

	private enum Position {
		FIRST, LAST
	}

	public Timestamp getLastSeenTimeOfMemberInTopic(Topic topic, Member member) {
		CriteriaHelper<TopicSeenByMember> criteriaHelper = new CriteriaHelper<>(TopicSeenByMember.class, em,
				CriteriaType.SELECT);
		CriteriaQuery<TopicSeenByMember> criteriaQuery = criteriaHelper.getCriteriaQuery();
		Root<TopicSeenByMember> rootEntity = criteriaHelper.getRootEntity();
		CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();

		criteriaQuery.where(
				criteriaBuilder.and(criteriaBuilder.equal(rootEntity.get(TopicSeenByMember_.memberId), member.getId()),
						criteriaBuilder.equal(rootEntity.get(TopicSeenByMember_.topicId), topic.getId())));

		criteriaQuery.select(rootEntity);

		try {
			TopicSeenByMember lastSeenTime = em.createQuery(criteriaQuery).getSingleResult();
			return lastSeenTime.getSeenTime();
		} catch (NoResultException e) {
			// Has no record for the given topic and member.
			// Member has not seen the topic yet.
			return null;
		}
	}

	public Topic getTopicWithLastPostFromSubcategory(Subcategory subcategory) {
		CriteriaHelper<Topic> topicCriteriaHelper = new CriteriaHelper<>(Topic.class, em, CriteriaType.SELECT);
		Root<Topic> topicRoot = topicCriteriaHelper.getRootEntity();
		CriteriaQuery<Topic> criteriaQuery = topicCriteriaHelper.getCriteriaQuery();
		CriteriaBuilder criteriaBuilder = topicCriteriaHelper.getCriteriaBuilder();

		Join<Topic, Subcategory> subcategoryJoin = topicRoot.join(Topic_.subcategoryBySubcategoryId);
		Join<Topic, Post> postJoin = topicRoot.join(Topic_.postsById);

		criteriaQuery.groupBy(subcategoryJoin.get(Subcategory_.id));
		criteriaQuery.where(criteriaBuilder.equal(subcategoryJoin.get(Subcategory_.id), subcategory.getId()));
		criteriaQuery.orderBy(criteriaBuilder.desc(postJoin.get(Post_.time)));

		criteriaQuery.select(topicRoot);

		return em.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
	}

	public boolean hasTopicUnreadPostsByMember(Topic topic, Member member) {
		Timestamp lastRead = getLastSeenTimeOfMemberInTopic(topic, member);
		if (lastRead != null) {
			Post lastPost = getLastPostFromTopic(topic);
			if (lastPost != null) {
				Timestamp lastPostTime = lastPost.getTime();
				if (lastRead.after(lastPostTime) || lastRead.equals(lastRead)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public List<Topic> getCreatedTopicsByMember(Member member) {
		CriteriaHelper<Topic> topicCriteriaHelper = new CriteriaHelper<>(Topic.class, em, CriteriaType.SELECT);
		;
		Root<Topic> topicRoot = topicCriteriaHelper.getRootEntity();
		CriteriaQuery<Topic> criteriaQuery = topicCriteriaHelper.getCriteriaQuery();
		CriteriaBuilder criteriaBuilder = topicCriteriaHelper.getCriteriaBuilder();

		Join<Topic, Post> postJoin = topicRoot.join(Topic_.postsById);

		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(postJoin.get(Post_.memberId), member.getId()),
				criteriaBuilder.equal(postJoin.get(Post_.postNumber), 1)));

		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (NoResultException e) {
			// Has no created Topic.
			return Collections.emptyList();
		}
	}

	private Post getPostFromTopicAtPosition(Topic topic, Position ordering) {
		CriteriaHelper<Post> postCriteriaHelper = new CriteriaHelper<>(Post.class, em, CriteriaType.SELECT);
		Root<Post> root = postCriteriaHelper.getRootEntity();
		CriteriaBuilder criteriaBuilder = postCriteriaHelper.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = postCriteriaHelper.getCriteriaQuery();

		criteriaQuery.where(criteriaBuilder.equal(root.get(Post_.topicId), topic.getId()));
		if (ordering == Position.FIRST) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Post_.time)));
		} else if (ordering == Position.LAST) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Post_.time)));
		} else {
			throw new IllegalArgumentException("Unknown ordering as parameter.");
		}

		return em.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
	}

	public Post getFirstPostFromTopic(Topic topic) {
		return getPostFromTopicAtPosition(topic, Position.FIRST);
	}

	public Post getLastPostFromTopic(Topic topic) {
		return getPostFromTopicAtPosition(topic, Position.LAST);
	}

	public List<Post> getLikedPostsOfMember(Member member) {
		CriteriaHelper<Post> postCriteriaHelper = new CriteriaHelper<>(Post.class, em, CriteriaType.SELECT);
		Root<Post> root = postCriteriaHelper.getRootEntity();
		CriteriaBuilder criteriaBuilder = postCriteriaHelper.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = postCriteriaHelper.getCriteriaQuery();

		// Posts that has no like will not be included
		Join<Post, MemberLike> likeJoin = root.join(Post_.likesById, JoinType.INNER);

		criteriaQuery.where(criteriaBuilder.equal(root.get(Post_.memberId), member.getId()));

		criteriaQuery.select(root).distinct(true);

		try {
			return em.createQuery(criteriaQuery).getResultList();
		} catch (NoResultException e) {
			// Has no likes.
			return Collections.emptyList();
		}
	}

}
