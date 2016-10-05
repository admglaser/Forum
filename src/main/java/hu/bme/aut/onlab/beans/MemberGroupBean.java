package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.MemberGroup;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class MemberGroupBean extends BaseBean<MemberGroup> {

    @PersistenceContext
    EntityManager entityManager;

    public MemberGroupBean() {
        super(MemberGroup.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}