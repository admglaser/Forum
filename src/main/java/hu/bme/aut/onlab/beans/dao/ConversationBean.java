package hu.bme.aut.onlab.beans.dao;

import hu.bme.aut.onlab.model.Conversation;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class ConversationBean extends BaseBean<Conversation> {

	public ConversationBean() {
		super(Conversation.class);
	}

}
