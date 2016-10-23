package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.ConversationSeenByMember;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class ConversationSeenByMemberBean extends BaseBean<ConversationSeenByMember> {

    @PersistenceContext
    private EntityManager entityManager;

    public ConversationSeenByMemberBean() {
        super(ConversationSeenByMember.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
