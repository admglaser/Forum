package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Message;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class MessageBean extends BaseBean<Message> {

    @PersistenceContext
    EntityManager entityManager;

    public MessageBean() {
        super(Message.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}