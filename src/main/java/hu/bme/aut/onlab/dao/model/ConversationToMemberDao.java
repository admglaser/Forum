package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.ConversationToMember;

@LocalBean
@Stateless
public class ConversationToMemberDao extends BaseDao<ConversationToMember> {

	public ConversationToMemberDao() {
		super(ConversationToMember.class);
	}

}
