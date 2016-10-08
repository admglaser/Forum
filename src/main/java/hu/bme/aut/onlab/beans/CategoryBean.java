package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.Category;
import hu.bme.aut.onlab.model.Category_;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Subcategory_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class CategoryBean extends BaseBean<Category> {

    @PersistenceContext
    EntityManager entityManager;

    public CategoryBean() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    public List<Tuple> testJoinedFind() {
        CriteriaHelper<Category> categoryCriteriaHelper = createTupleQueryHelper();
        Root<Category> rootEntity = categoryCriteriaHelper.getRootEntity();
        CriteriaQuery<Tuple> criteria = categoryCriteriaHelper.getCriteriaTupleQuery();

        Join<Category, Subcategory> subcategoryJoin = rootEntity.join(Category_.subcategoriesById);

        criteria.multiselect(
                  rootEntity.get(Category_.id)
                , rootEntity.get(Category_.title)
                , subcategoryJoin.get(Subcategory_.title)
                , subcategoryJoin.get(Subcategory_.desc)
        );

        return executeTupleQuery(categoryCriteriaHelper);
    }
}
