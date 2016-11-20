package hu.bme.aut.onlab.bean.dao;

import hu.bme.aut.onlab.model.TopicSubscription;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class TopicSubscriptionBean extends BaseBean<TopicSubscription> {

    public TopicSubscriptionBean() {
        super(TopicSubscription.class);
    }
}
