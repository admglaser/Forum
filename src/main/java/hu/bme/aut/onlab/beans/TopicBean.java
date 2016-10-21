package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.model.Post_;
import hu.bme.aut.onlab.model.Subcategory_;
import hu.bme.aut.onlab.model.Topic_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

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

    public Topic getTopicWithLastPostFromSubcategory(long subcategoryId) {
        CriteriaHelper<Topic> topicCriteriaHelper = createQueryHelper();
        Root<Topic> topicRoot = topicCriteriaHelper.getRootEntity();
        CriteriaQuery<Topic> criteriaQuery = topicCriteriaHelper.getCriteriaQuery();
        CriteriaBuilder criteriaBuilder = topicCriteriaHelper.getCriteriaBuilder();

        Join<Topic, Subcategory> subcategoryJoin = topicRoot.join(Topic_.subcategoryBySubcategoryId);
        Join<Topic, Post> postJoin = topicRoot.join(Topic_.postsById);

        criteriaQuery.groupBy(subcategoryJoin.get(Subcategory_.id));
        criteriaQuery.where(criteriaBuilder.equal(subcategoryJoin.get(Subcategory_.id), subcategoryId));
        criteriaQuery.orderBy(criteriaBuilder.desc(postJoin.get(Post_.time)));

        criteriaQuery.select(topicRoot);

        return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
    }
}