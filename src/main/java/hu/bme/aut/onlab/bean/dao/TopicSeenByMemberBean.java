package hu.bme.aut.onlab.bean.dao;

import hu.bme.aut.onlab.model.TopicSeenByMember;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class TopicSeenByMemberBean extends BaseBean<TopicSeenByMember> {

	public TopicSeenByMemberBean() {
		super(TopicSeenByMember.class);
	}

}
