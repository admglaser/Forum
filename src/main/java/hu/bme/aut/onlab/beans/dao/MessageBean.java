package hu.bme.aut.onlab.beans.dao;

import hu.bme.aut.onlab.model.Message;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class MessageBean extends BaseBean<Message> {

    public MessageBean() {
        super(Message.class);
    }
}
