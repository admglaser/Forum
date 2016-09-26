package hu.bme.aut.onlab.model.meta;

import hu.bme.aut.onlab.model.Post;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ {

	public static volatile SingularAttribute<Post, Integer> topicId;
	public static volatile SingularAttribute<Post, Integer> id;
	public static volatile SingularAttribute<Post, Integer> postId;
	public static volatile SingularAttribute<Post, String> text;
	public static volatile SingularAttribute<Post, Timestamp> time;
	public static volatile SingularAttribute<Post, Integer> memberId;

}

