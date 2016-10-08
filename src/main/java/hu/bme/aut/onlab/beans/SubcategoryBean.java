package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class SubcategoryBean extends BaseBean<Subcategory> {


    @PersistenceContext
    EntityManager entityManager;

    public SubcategoryBean() {
        super(Subcategory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    public List<Tuple> testJoinedFind() {
        CriteriaHelper<Subcategory> categoryCriteriaHelper = createTupleQueryHelper();
        Root<Subcategory> rootEntity = categoryCriteriaHelper.getRootEntity();
        CriteriaQuery<Tuple> criteria = categoryCriteriaHelper.getCriteriaTupleQuery();

        Join<Subcategory, Category> categoryJoin = rootEntity.join(Subcategory_.categoryByCategoryId);
        Join<Subcategory, Topic> topicJoin = rootEntity.join(Subcategory_.topicsById, JoinType.LEFT);

        criteria.multiselect(
                rootEntity.get(Subcategory_.title)
                , rootEntity.get(Subcategory_.desc)
                , categoryJoin.get(Category_.title)
                , topicJoin.get(Topic_.title)
        );

        return executeTupleQuery(categoryCriteriaHelper);
    }
}