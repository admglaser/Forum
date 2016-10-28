package hu.bme.aut.onlab.beans.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Topic;


@LocalBean
@Stateless
public class TopicBean extends BaseBean<Topic> {

	public TopicBean() {
		super(Topic.class);
	}

}
