package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.SubcategorySubscription;

@LocalBean
@Stateless
public class SubcategorySubscriptionBean extends BaseDao<SubcategorySubscription> {

    public SubcategorySubscriptionBean() {
        super(SubcategorySubscription.class);
    }
}
