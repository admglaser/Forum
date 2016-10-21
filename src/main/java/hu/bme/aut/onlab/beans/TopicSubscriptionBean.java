package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.TopicSubscription;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class TopicSubscriptionBean extends BaseBean<TopicSubscription> {

    @PersistenceContext
    private EntityManager entityManager;

    public TopicSubscriptionBean() {
        super(TopicSubscription.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}