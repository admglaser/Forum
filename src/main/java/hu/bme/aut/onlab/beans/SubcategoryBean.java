package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.model.Subcategory_;
import hu.bme.aut.onlab.model.Topic_;
import hu.bme.aut.onlab.model.Category_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class SubcategoryBean extends BaseBean<Subcategory> {


    @PersistenceContext
    private EntityManager entityManager;

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