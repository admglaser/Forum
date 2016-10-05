package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Notification;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class NotificationBean extends BaseBean<Notification> {

    @PersistenceContext
    EntityManager entityManager;

    public NotificationBean() {
        super(Notification.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}