package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Conversation;

@LocalBean
@Stateless
public class ConversationDao extends BaseDao<Conversation> {

	public ConversationDao() {
		super(Conversation.class);
	}

}
