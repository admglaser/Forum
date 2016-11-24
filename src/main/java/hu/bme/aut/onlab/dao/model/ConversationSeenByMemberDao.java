package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.ConversationSeenByMember;

@LocalBean
@Stateless
public class ConversationSeenByMemberDao extends BaseDao<ConversationSeenByMember> {

	public ConversationSeenByMemberDao() {
		super(ConversationSeenByMember.class);
	}

}
