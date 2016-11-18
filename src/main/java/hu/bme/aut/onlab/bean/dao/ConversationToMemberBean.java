package hu.bme.aut.onlab.bean.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.ConversationToMember;

@LocalBean
@Stateless
public class ConversationToMemberBean extends BaseBean<ConversationToMember> {

	public ConversationToMemberBean() {
		super(ConversationToMember.class);
	}

}
