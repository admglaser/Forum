package hu.bme.aut.onlab.bean;

import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.util.NavigationUtils;
import hu.bme.aut.onlab.util.NotificationType;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

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

	private List<Member> getTopicSubscribers(Topic topic) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> query = builder.createQuery(Member.class);
		Root<Member> memberRoot = query.from(Member.class);
		
		ListJoin<Member, TopicSubscription> topicSubscribtionJoin = memberRoot.join(Member_.topicSubscriptions);
		
		query.where(
				builder.equal(topicSubscribtionJoin.get(TopicSubscription_.topicId), topic.getId())
		);
		
		try {
			return em.createQuery(query).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	private List<Member> getSubcategorySubscribers(Subcategory subcategory) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> query = builder.createQuery(Member.class);
		Root<Member> memberRoot = query.from(Member.class);
		
		ListJoin<Member, SubcategorySubscription> subcategorySubscribtion = memberRoot.join(Member_.subcategorySubscriptions);
		
		query.where(
				builder.equal(subcategorySubscribtion.get(SubcategorySubscription_.subcategoryId), subcategory.getId())
		);
		
		try {
			return em.createQuery(query).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public void addNewReply(Post post) {
		Topic topic = post.getTopic();
		
		List<Member> subscribers = getTopicSubscribers(topic);
		
		for (Member targetMember : subscribers) {
			int highestNotificationNumber = getHighestNotificationNumber(targetMember);
			
			NotificationEvent notificationEvent = new NotificationEvent();
			notificationEvent.setType(NotificationType.NEW_REPLY.getId());
			int pageNumber = NavigationUtils.getPageOfElement(post.getPostNumber());
			notificationEvent.setLink(String.format("#/topic/%d/%d", topic.getId(), pageNumber));
			notificationEvent.setText("There is a new reply in this topic.");
			em.persist(notificationEvent);

			Notification notification = new Notification();
			notification.setNotificationNumber(highestNotificationNumber+1);
			notification.setSeen(false);
			notification.setMember(targetMember);
			notification.setNotificationEvent(notificationEvent);
			em.persist(notification);
		}
	}

	public void addNewTopic(Topic topic) {
		Subcategory subcategory = topic.getSubcategory();
		
		List<Member> subscribers = getSubcategorySubscribers(subcategory);
		
		for (Member targetMember : subscribers) {
			int highestNotificationNumber = getHighestNotificationNumber(targetMember);
			
			NotificationEvent notificationEvent = new NotificationEvent();
			notificationEvent.setType(NotificationType.NEW_TOPIC.getId());
			notificationEvent.setLink(String.format("#/topic/%d", topic.getId()));
			notificationEvent.setText("There is a new topic in this category.");
			em.persist(notificationEvent);

			Notification notification = new Notification();
			notification.setNotificationNumber(highestNotificationNumber+1);
			notification.setSeen(false);
			notification.setMember(targetMember);
			notification.setNotificationEvent(notificationEvent);
			em.persist(notification);
		}
		
	}
	
	public void addLike(Member member, Post post) {
		Topic topic = post.getTopic();
		Member targetMember = post.getMember();
		int highestNotificationNumber = getHighestNotificationNumber(targetMember);
		int pageNumber = NavigationUtils.getPageOfElement(post.getPostNumber());
		
		NotificationEvent notificationEvent = new NotificationEvent();
		notificationEvent.setType(NotificationType.LIKE.getId());
		notificationEvent.setLink(String.format("#/topic/%d/%d", topic.getId(), pageNumber));
		notificationEvent.setText(String.format("%s liked a post of yours.", targetMember.getDisplayName()));
		em.persist(notificationEvent);

		Notification notification = new Notification();
		notification.setNotificationNumber(highestNotificationNumber+1);
		notification.setSeen(false);
		notification.setMember(targetMember);
		notification.setNotificationEvent(notificationEvent);
		em.persist(notification);
	}
	
}
