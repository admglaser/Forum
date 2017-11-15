package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Message;

@LocalBean
@Stateless
public class MessageDao extends BaseDao<Message> {

    public MessageDao() {
        super(Message.class);
    }
}
