package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Subcategory;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subcategory.class)
public abstract class Subcategory_ {

	public static volatile SingularAttribute<Subcategory, Integer> id;
	public static volatile SingularAttribute<Subcategory, Integer> title;
	public static volatile SingularAttribute<Subcategory, Integer> categoryId;
	public static volatile SingularAttribute<Subcategory, Integer> desc;

}

