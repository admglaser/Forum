package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.model.PermissionSet;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MemberGroup.class)
public abstract class MemberGroup_ {

	public static volatile SingularAttribute<MemberGroup, String> prefix;
	public static volatile SetAttribute<MemberGroup, PermissionSet> permissionSets;
	public static volatile SingularAttribute<MemberGroup, Integer> id;
	public static volatile SingularAttribute<MemberGroup, String> postfix;
	public static volatile SingularAttribute<MemberGroup, String> title;

}

