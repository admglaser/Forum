package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Post;

@LocalBean
@Stateless
public class PostBean extends BaseDao<Post> {

	public PostBean() {
		super(Post.class);
	}

}
