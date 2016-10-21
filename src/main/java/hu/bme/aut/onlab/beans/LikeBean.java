package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Like;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class LikeBean extends BaseBean<Like> {

    @PersistenceContext
    private EntityManager entityManager;

    public LikeBean() {
        super(Like.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}