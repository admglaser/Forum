package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Conversation;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class ConversationBean extends BaseBean<Conversation> {

    @PersistenceContext
    EntityManager entityManager;

    public ConversationBean() {
        super(Conversation.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}