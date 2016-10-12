package hu.bme.aut.onlab.data;

import hu.bme.aut.onlab.model.Category;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Logan on 2016.09.20..
 */
@LocalBean
@Stateless
public class CategoryBean {

    @PersistenceContext(name = "FORUM", unitName = "FORUM")
    EntityManager entityManager;

    public List<Category> findAllOrderedByTitle() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = cb.createQuery(Category.class);
        Root<Category> category = criteria.from(Category.class);
        criteria.select(category).orderBy(cb.asc(category.get("title")));
        return entityManager.createQuery(criteria).getResultList();
    }

}
