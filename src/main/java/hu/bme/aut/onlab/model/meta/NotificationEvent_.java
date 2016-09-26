package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.NotificationEvent;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NotificationEvent.class)
public abstract class NotificationEvent_ {

	public static volatile SingularAttribute<NotificationEvent, String> link;
	public static volatile SingularAttribute<NotificationEvent, Integer> id;
	public static volatile SingularAttribute<NotificationEvent, Timestamp> time;
	public static volatile SingularAttribute<NotificationEvent, Integer> type;

}

