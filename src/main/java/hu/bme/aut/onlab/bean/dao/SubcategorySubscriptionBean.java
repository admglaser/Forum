package hu.bme.aut.onlab.bean.dao;

import hu.bme.aut.onlab.model.SubcategorySubscription;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class SubcategorySubscriptionBean extends BaseBean<SubcategorySubscription> {

    public SubcategorySubscriptionBean() {
        super(SubcategorySubscription.class);
    }
}
