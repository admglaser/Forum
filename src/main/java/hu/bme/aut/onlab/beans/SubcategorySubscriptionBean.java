package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.SubcategorySubscription;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class SubcategorySubscriptionBean extends BaseBean<SubcategorySubscription> {

    @PersistenceContext
    private EntityManager entityManager;

    public SubcategorySubscriptionBean() {
        super(SubcategorySubscription.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}