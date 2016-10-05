package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.NotificationEvent;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class NotificationEventBean extends BaseBean<NotificationEvent> {

    @PersistenceContext
    EntityManager entityManager;

    public NotificationEventBean() {
        super(NotificationEvent.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}