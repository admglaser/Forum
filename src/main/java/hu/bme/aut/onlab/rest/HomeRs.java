package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.CategoryBean;
import hu.bme.aut.onlab.beans.PostBean;
import hu.bme.aut.onlab.beans.TopicBean;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.util.Formatter;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@Path("/home")
public class HomeRs  {
    @EJB
    private CategoryBean categoryBean;

    @EJB
    private PostBean postBean;

    @EJB
    private TopicBean topicBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedCategory() {
        JSONArray result = new JSONArray();
        List<Category> categories = categoryBean.findAllEntity();
        for (Category category : categories) {
            JSONObject categoryJson = new JSONObject();

            JSONArray subcategoriesJsonArray = new JSONArray();

            for (Subcategory subcategory : category.getSubcategoriesById()) {
                JSONObject subcategoryJson = new JSONObject();

                if (! subcategory.getTopicsById().isEmpty() ) {
                    Topic lastTopic = topicBean.getTopicWithLastPostPerSubcategory(subcategory.getId());
                    Post lastPost = postBean.getLastPostPerTopic(lastTopic.getId());
                    Member memberOfLastPost = lastPost.getMemberByMemberId();

                    subcategoryJson.put("postCount", lastTopic.getPostsById().size());
                    subcategoryJson.put("lastTitle", lastTopic.getTitle());
                    subcategoryJson.put("lastPoster", memberOfLastPost.getDisplayName());
                    subcategoryJson.put("lastDate", Formatter.formatTimeStamp(lastPost.getTime()));
                    subcategoryJson.put("subcategoryLink", "#/subcategory/" + subcategory.getId());
                    subcategoryJson.put("postLink", "#/topic/" + lastTopic.getId()+ "/" + lastPost.getPostNumber());
                    subcategoryJson.put("userLink", "#/user/" + memberOfLastPost.getId());
                }

                subcategoryJson.put("title", subcategory.getTitle());
                subcategoryJson.put("desc", subcategory.getDesc());
                subcategoryJson.put("topicCount", subcategory.getTopicsById().size());
                subcategoryJson.put("hasLasTopic", ! subcategory.getTopicsById().isEmpty());

                subcategoriesJsonArray.put(subcategoryJson);
            }

            categoryJson.put("title", category.getTitle());
            categoryJson.put("subcategories", subcategoriesJsonArray);

            result.put(categoryJson);
        }

        return result.toString();
    }
}
