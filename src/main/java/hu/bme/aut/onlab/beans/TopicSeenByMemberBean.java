package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.TopicSeenByMember;
import hu.bme.aut.onlab.model.TopicSeenByMember_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class TopicSeenByMemberBean extends BaseBean<TopicSeenByMember> {

    @PersistenceContext
    private EntityManager entityManager;

    public TopicSeenByMemberBean() {
        super(TopicSeenByMember.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Timestamp getLastSeenTimeOfMember(int topic_id, int member_id) {
        CriteriaHelper<TopicSeenByMember> criteriaHelper = createQueryHelper();
        CriteriaQuery<TopicSeenByMember> criteriaQuery = criteriaHelper.getCriteriaQuery();
        Root<TopicSeenByMember> rootEntity = criteriaHelper.getRootEntity();
        CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();

        criteriaQuery.where(criteriaBuilder.and(
                        criteriaBuilder.equal(rootEntity.get(TopicSeenByMember_.memberId), member_id),
                        criteriaBuilder.equal(rootEntity.get(TopicSeenByMember_.topicId), topic_id))
        );

        criteriaQuery.select(rootEntity);

        try {
            TopicSeenByMember lastSeenTime = getEntityManager().createQuery(criteriaQuery).getSingleResult();
            return lastSeenTime.getSeenTime();
        } catch (NoResultException e) {
            // Has no record for the given topic and member.
            //   Member has not seen the topic yet.
            return null;
        }
    }
}
