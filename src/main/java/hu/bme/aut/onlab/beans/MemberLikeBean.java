package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.MemberLike;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class MemberLikeBean extends BaseBean<MemberLike> {

    @PersistenceContext
    private EntityManager entityManager;

    public MemberLikeBean() {
        super(MemberLike.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}