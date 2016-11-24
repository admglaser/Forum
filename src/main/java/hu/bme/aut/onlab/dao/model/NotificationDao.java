package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Notification;

@LocalBean
@Stateless
public class NotificationDao extends BaseDao<Notification> {

	public NotificationDao() {
		super(Notification.class);
	}

}
