package hu.bme.aut.onlab.beans;

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

import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Conversation_;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Member_;
import hu.bme.aut.onlab.util.NavigationUtils;

@LocalBean
@Stateless
public class MessagingService {

	@PersistenceContext
	private EntityManager em;

	public int getConversationsCount(Member member) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Conversation> conversationRoot = query.from(Conversation.class);

		ListJoin<Conversation, Member> memberJoin = conversationRoot.join(Conversation_.members);
		
		query.select(builder.count(conversationRoot));

		query.where(builder.equal(memberJoin.get(Member_.id), member.getId()));
	
		try {
			return em.createQuery(query).getSingleResult().intValue();
		} catch (NoResultException e) {
			return 0;
		}
	}

	public List<Conversation> getConversations(Member member, int pageNumber) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Conversation> query = builder.createQuery(Conversation.class);
		Root<Conversation> conversationRoot = query.from(Conversation.class);

		ListJoin<Conversation, Member> join = conversationRoot.join(Conversation_.members);
		
		query.where(builder.equal(join.get(Member_.id), member.getId()));

		try {
			return em.createQuery(query)
					.setFirstResult((pageNumber-1)*NavigationUtils.ELEMENTS_PER_PAGE)
					.setMaxResults(NavigationUtils.ELEMENTS_PER_PAGE)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	
}
