package hu.bme.aut.onlab.beans.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Notification;

@LocalBean
@Stateless
public class NotificationBean extends BaseBean<Notification> {

	public NotificationBean() {
		super(Notification.class);
	}

}
