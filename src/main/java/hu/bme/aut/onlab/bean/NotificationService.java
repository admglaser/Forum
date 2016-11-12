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
import javax.persistence.criteria.Root;

import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Notification;
import hu.bme.aut.onlab.model.Notification_;

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
	
}
