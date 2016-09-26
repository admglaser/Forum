package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Message;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, Integer> messageNumber;
	public static volatile SingularAttribute<Message, Integer> conversationId;
	public static volatile SingularAttribute<Message, Integer> id;
	public static volatile SingularAttribute<Message, String> text;
	public static volatile SingularAttribute<Message, Timestamp> time;
	public static volatile SingularAttribute<Message, Byte> seen;

}

