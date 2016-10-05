package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Member;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class MemberBean extends BaseBean<Member> {

    @PersistenceContext
    EntityManager entityManager;

    public MemberBean() {
        super(Member.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}