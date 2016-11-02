package hu.bme.aut.onlab.beans.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Post;

@LocalBean
@Stateless
public class PostBean extends BaseBean<Post> {

	public PostBean() {
		super(Post.class);
	}

}
