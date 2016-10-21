package hu.bme.aut.onlab.beans.view;

import hu.bme.aut.onlab.beans.BaseBean;
import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.view.CategoriesSummary;
import hu.bme.aut.onlab.model.view.CategoriesSummary_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class CategoriesSummaryBean extends BaseBean<CategoriesSummary> {

    @PersistenceContext
    EntityManager entityManager;

    public CategoriesSummaryBean() {
        super(CategoriesSummary.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<Tuple> getView() {
        CriteriaHelper<CategoriesSummary> categoryCriteriaHelper = createTupleQueryHelper();
        Root<CategoriesSummary> rootEntity = categoryCriteriaHelper.getRootEntity();
        CriteriaQuery<Tuple> criteria = categoryCriteriaHelper.getCriteriaTupleQuery();

        criteria.multiselect(
                rootEntity.get(CategoriesSummary_.categoryId),
                rootEntity.get(CategoriesSummary_.categoryTitle),
                rootEntity.get(CategoriesSummary_.subcategoryId),
                rootEntity.get(CategoriesSummary_.subcategoryTitle),
                rootEntity.get(CategoriesSummary_.subcategoryDescription),
                rootEntity.get(CategoriesSummary_.topicCount),
                rootEntity.get(CategoriesSummary_.postCount),
                rootEntity.get(CategoriesSummary_.lastPostTopicId),
                rootEntity.get(CategoriesSummary_.lastPostTopicTitle),
                rootEntity.get(CategoriesSummary_.lastPostPostId),
                rootEntity.get(CategoriesSummary_.lastPostMemberId),
                rootEntity.get(CategoriesSummary_.lastPostMemberName),
                rootEntity.get(CategoriesSummary_.lastPostPostTime)
        );

        return executeTupleQuery(categoryCriteriaHelper);
    }
}
