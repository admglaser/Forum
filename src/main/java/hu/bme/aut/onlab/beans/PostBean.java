package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Post_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class PostBean extends BaseBean<Post> {

    @PersistenceContext
    private EntityManager entityManager;

    public PostBean() {
        super(Post.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public Post getLastPostFromTopic(int topicId) {
        CriteriaHelper<Post> postCriteriaHelper = createQueryHelper();
        Root<Post> root = postCriteriaHelper.getRootEntity();
        CriteriaBuilder criteriaBuilder = postCriteriaHelper.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = postCriteriaHelper.getCriteriaQuery();

        criteriaQuery.where(criteriaBuilder.equal(root.get(Post_.topicId), topicId));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Post_.time)));
        return entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
    }

    public boolean hasTopicUnreadPost(int topicId, Timestamp lastRead) {
        if (lastRead != null) {
            Post lastPost = getLastPostFromTopic(topicId);
            if (lastPost != null) {
                Timestamp lastPostTime = lastPost.getTime();

                if (lastRead.after(lastPostTime) || lastRead.equals(lastRead)) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }
}