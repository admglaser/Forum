package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Member;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Conversation.class)
public abstract class Conversation_ {

	public static volatile SingularAttribute<Conversation, Integer> messageCount;
	public static volatile SetAttribute<Conversation, Member> members;
	public static volatile SingularAttribute<Conversation, Integer> id;
	public static volatile SingularAttribute<Conversation, String> title;

}

