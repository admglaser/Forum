package hu.bme.aut.onlab.rest;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.bme.aut.onlab.beans.ForumReadService;
import hu.bme.aut.onlab.beans.LoginService;
import hu.bme.aut.onlab.beans.dao.CategoryBean;
import hu.bme.aut.onlab.beans.dao.PostBean;
import hu.bme.aut.onlab.beans.dao.TopicBean;
import hu.bme.aut.onlab.model.Category;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.util.Formatter;

@Path("/home")
public class HomeRs  {
   
    @EJB
    private ForumReadService forumReadService;
    
    @EJB 
    private LoginService loginService;
    
    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private TopicBean topicBean;
    
    @EJB
    private PostBean postBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedCategory() {
        JSONArray result = new JSONArray();
        List<Category> categories = categoryBean.findAllEntity();
        for (Category category : categories) {
            JSONObject categoryJson = new JSONObject();

            JSONArray subcategoriesJsonArray = new JSONArray();

            categoryJson.put("title", category.getTitle());
            categoryJson.put("subcategories", subcategoriesJsonArray);
            for (Subcategory subcategory : category.getSubcategories()) {
                JSONObject subcategoryJson = new JSONObject();
                Collection<Topic> topics = subcategory.getTopics();

                int totalPosts = 0;
                for (Topic topic : topics) {
                	totalPosts += topic.getPosts().size();
                }
                
                subcategoryJson.put("title", subcategory.getTitle());
                subcategoryJson.put("desc", subcategory.getDesc());
                subcategoryJson.put("topicCount", topics.size());
                subcategoryJson.put("postCount", totalPosts);
                subcategoryJson.put("hasLasTopic", ! topics.isEmpty());

                if (!topics.isEmpty() ) {
                    Topic lastTopic = forumReadService.getTopicWithLastPostFromSubcategory(subcategory);
                    Post lastPost = forumReadService.getLastPostFromTopic(lastTopic);
                    Member memberOfLastPost = lastPost.getMember();

                    subcategoryJson.put("lastTitle", lastTopic.getTitle());
                    subcategoryJson.put("lastPoster", memberOfLastPost.getDisplayName());
                    subcategoryJson.put("lastDate", Formatter.formatTimeStamp(lastPost.getTime()));
                    subcategoryJson.put("subcategoryLink", "#/subcategory/" + subcategory.getId());
                    subcategoryJson.put("postLink", "#/topic/" + lastTopic.getId()+ "/" + lastPost.getPostNumber());
                    subcategoryJson.put("userLink", "#/user/" + memberOfLastPost.getId());
                    subcategoryJson.put("unread", forumReadService.hasTopicUnreadPostsByMember(lastTopic, loginService.getCurrentMember()));
                }

                subcategoriesJsonArray.put(subcategoryJson);
            }

            result.put(categoryJson);
        }

        return result.toString();
    }
}
