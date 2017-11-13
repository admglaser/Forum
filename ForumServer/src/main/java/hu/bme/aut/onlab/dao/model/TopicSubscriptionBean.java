package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.TopicSubscription;

@LocalBean
@Stateless
public class TopicSubscriptionBean extends BaseDao<TopicSubscription> {

    public TopicSubscriptionBean() {
        super(TopicSubscription.class);
    }
}
