package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Post;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class PostBean extends BaseBean<Post> {

    @PersistenceContext
    EntityManager entityManager;

    public PostBean() {
        super(Post.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}