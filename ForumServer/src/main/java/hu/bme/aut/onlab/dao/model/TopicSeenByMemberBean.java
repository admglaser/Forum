package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.TopicSeenByMember;

@LocalBean
@Stateless
public class TopicSeenByMemberBean extends BaseDao<TopicSeenByMember> {

	public TopicSeenByMemberBean() {
		super(TopicSeenByMember.class);
	}

}
