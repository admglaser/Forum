package hu.bme.aut.onlab.bean;

import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;

import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Member_;
import hu.bme.aut.onlab.model.Notification;
import hu.bme.aut.onlab.model.NotificationEvent;
import hu.bme.aut.onlab.model.Notification_;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Post_;
import hu.bme.aut.onlab.util.NavigationUtils;
import hu.bme.aut.onlab.util.NotificationType;

@LocalBean
@Stateless
public class NotificationService {

	@PersistenceContext
	private EntityManager em;
	
	public List<Notification> getLastFiveNotifications(Member member) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
		Root<Notification> notificationRoot = query.from(Notification.class);
		
		query.where(
				builder.equal(notificationRoot.get(Notification_.memberId), member.getId()));
		
		query.select(notificationRoot);
		
		query.orderBy(builder.desc(notificationRoot.get(Notification_.notificationNumber)));
		
		try {
			return em.createQuery(query).setMaxResults(5).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public boolean isNotificationUnread(Notification notification) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
		Root<Notification> notificationRoot = query.from(Notification.class);

		query.where(
				builder.and(
						builder.equal(notificationRoot.get(Notification_.id), notification.getId()),
						builder.equal(notificationRoot.get(Notification_.seen), false)));

		query.select(notificationRoot);
		
		try {
			List<Notification> resultList = em.createQuery(query).getResultList();
			return !resultList.isEmpty();
		} catch (NoResultException e) {
			return true;
		}
	}
	
	public int getHighestNotificationNumber(Member member) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
		Root<Notification> notificationRoot = query.from(Notification.class);
		
		query.where(builder.equal(notificationRoot.get(Notification_.memberId), member.getId()));
		
		query.orderBy(builder.desc(notificationRoot.get(Notification_.notificationNumber)));
		
		query.select(notificationRoot);
		
		try {
			Notification result = em.createQuery(query).setMaxResults(1).getSingleResult();
			return result.getNotificationNumber();
		} catch (NoResultException e) {
			return 0;
		}
	}
	
	public void addMention(Member member, Member targetMember, Post post) {
		int highestNotificationNumber = getHighestNotificationNumber(targetMember);
		
		NotificationEvent notificationEvent = new NotificationEvent();
		notificationEvent.setType(NotificationType.MENTION.getId());
		int topicId = post.getTopic().getId();
		int pageNumber = NavigationUtils.getPageOfElement(post.getPostNumber());
		notificationEvent.setLink(String.format("#/topic/%d/%d", topicId, pageNumber));
		notificationEvent.setText(String.format("%s mentioned you in this post", member.getDisplayName()));
		em.persist(notificationEvent);
		
		Notification notification = new Notification();
		notification.setNotificationNumber(highestNotificationNumber+1);
		notification.setSeen(false);
		notification.setMember(targetMember);
		notification.setNotificationEvent(notificationEvent);
		em.persist(notification);
	}
	
	private Member getMemberOfPost(int topicId, int postNumber) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> query = builder.createQuery(Member.class);
		Root<Member> memberRoot = query.from(Member.class);
		
		ListJoin<Member, Post> postJoin = memberRoot.join(Member_.posts);
		
		query.where(
				builder.and(
						builder.equal(postJoin.get(Post_.topicId), topicId),
						builder.equal(postJoin.get(Post_.postNumber), postNumber)
				)
		);
		
		
		try {
			return em.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addQuote(Member member, int topicId, int postNumber) {
		Member targetMember = getMemberOfPost(topicId, postNumber);
		
		int highestNotificationNumber = getHighestNotificationNumber(targetMember);
		
		NotificationEvent notificationEvent = new NotificationEvent();
		notificationEvent.setType(NotificationType.QUOTE.getId());
		int pageNumber = NavigationUtils.getPageOfElement(postNumber);
		notificationEvent.setLink(String.format("#/topic/%d/%d", topicId, pageNumber));
		notificationEvent.setText(String.format("%s quoted a post you made.", member.getDisplayName()));
		em.persist(notificationEvent);
		
		Notification notification = new Notification();
		notification.setNotificationNumber(highestNotificationNumber+1);
		notification.setSeen(false);
		notification.setMember(targetMember);
		notification.setNotificationEvent(notificationEvent);
		em.persist(notification);
		
	}
	
	//There is a new reply in this topic.
	//There is a new topic in this forum.
}
