package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.model.Conversation_;
import hu.bme.aut.onlab.model.Message_;
import hu.bme.aut.onlab.util.NavigationUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

@LocalBean
@Stateless
public class MessagingService {

    @PersistenceContext
    private EntityManager em;

    public List<Message> getMessagesOfConversation(Conversation conversation, int pageNumber) {
        CriteriaHelper<Message> conversationCriteriaHelper = new CriteriaHelper<>(Message.class, em, CriteriaHelper.CriteriaType.SELECT);
        Root<Message> messageRoot = conversationCriteriaHelper.getRootEntity();
        CriteriaBuilder criteriaBuilder = conversationCriteriaHelper.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = conversationCriteriaHelper.getCriteriaQuery();

        Join<Message, Conversation> conversationJoin = messageRoot.join(Message_.conversation);

        criteriaQuery.where(criteriaBuilder.equal(conversationJoin.get(Conversation_.id), conversation.getId()));

        criteriaQuery.orderBy(criteriaBuilder.asc(messageRoot.get(Message_.messageNumber)));

        criteriaQuery.select(messageRoot);

        try {
            return em.createQuery(criteriaQuery)
                    .setFirstResult((pageNumber-1) * NavigationUtils.ELEMENTS_PER_PAGE)
                    .setMaxResults(NavigationUtils.ELEMENTS_PER_PAGE)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public int getMessagesCountOfConversation(Conversation conversation) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Message> messageRoot = query.from(Message.class);

        Join<Message, Conversation> conversationJoin = messageRoot.join(Message_.conversation);

        query.where(criteriaBuilder.equal(conversationJoin.get(Conversation_.id), conversation.getId()));

        query.orderBy(criteriaBuilder.asc(messageRoot.get(Message_.messageNumber)));

        query.select(criteriaBuilder.count(messageRoot));

        try {
            return em.createQuery(query).getSingleResult().intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
}
