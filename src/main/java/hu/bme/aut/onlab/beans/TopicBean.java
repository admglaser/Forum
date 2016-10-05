package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Topic;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class TopicBean extends BaseBean<Topic> {

    @PersistenceContext
    EntityManager entityManager;

    public TopicBean() {
        super(Topic.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}