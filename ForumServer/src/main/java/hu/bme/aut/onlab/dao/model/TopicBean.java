package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Topic;

@LocalBean
@Stateless
public class TopicBean extends BaseDao<Topic> {

	public TopicBean() {
		super(Topic.class);
	}

}
