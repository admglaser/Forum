package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Member;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Member.class)
public abstract class Member_ {

	public static volatile SingularAttribute<Member, Date> birthday;
	public static volatile SingularAttribute<Member, Integer> likesCount;
	public static volatile SingularAttribute<Member, String> password;
	public static volatile SingularAttribute<Member, String> displayName;
	public static volatile SingularAttribute<Member, Integer> profileViewsCount;
	public static volatile SingularAttribute<Member, Integer> postCount;
	public static volatile SingularAttribute<Member, Integer> memberGroupId;
	public static volatile SingularAttribute<Member, Integer> messageid;
	public static volatile SingularAttribute<Member, Integer> id;
	public static volatile SingularAttribute<Member, String> userName;
	public static volatile SetAttribute<Member, Conversation> conversations;
	public static volatile SingularAttribute<Member, String> email;

}

