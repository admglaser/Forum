package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Permission;
import hu.bme.aut.onlab.model.PermissionSet;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ {

	public static volatile SingularAttribute<Permission, Byte> replyAllowed;
	public static volatile SetAttribute<Permission, PermissionSet> permissionSets;
	public static volatile SingularAttribute<Permission, Byte> readAllowed;
	public static volatile SingularAttribute<Permission, Integer> subcategoryId;
	public static volatile SingularAttribute<Permission, Integer> id;
	public static volatile SingularAttribute<Permission, Byte> startAllowed;

}

