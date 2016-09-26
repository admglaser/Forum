package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Notification;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notification.class)
public abstract class Notification_ {

	public static volatile SingularAttribute<Notification, Integer> notificationEventId;
	public static volatile SingularAttribute<Notification, Integer> id;
	public static volatile SingularAttribute<Notification, Byte> seen;
	public static volatile SingularAttribute<Notification, Integer> memberId;

}

