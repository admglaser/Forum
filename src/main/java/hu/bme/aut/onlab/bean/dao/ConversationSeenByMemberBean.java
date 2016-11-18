package hu.bme.aut.onlab.bean.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.ConversationSeenByMember;

@LocalBean
@Stateless
public class ConversationSeenByMemberBean extends BaseBean<ConversationSeenByMember> {

	public ConversationSeenByMemberBean() {
		super(ConversationSeenByMember.class);
	}

}
