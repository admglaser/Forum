package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.model.Permission;
import hu.bme.aut.onlab.model.PermissionSet;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PermissionSet.class)
public abstract class PermissionSet_ {

	public static volatile SingularAttribute<PermissionSet, Integer> permissionId;
	public static volatile SetAttribute<PermissionSet, Permission> permissions;
	public static volatile SingularAttribute<PermissionSet, Integer> id;
	public static volatile SetAttribute<PermissionSet, MemberGroup> memberGroups;

}

