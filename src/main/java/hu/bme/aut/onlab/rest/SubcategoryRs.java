package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.ForumReadService;
import hu.bme.aut.onlab.beans.LoginService;
import hu.bme.aut.onlab.beans.dao.*;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.NavigationUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.Format;
import java.util.List;

@Path("/subcategory")
public class SubcategoryRs {

    @EJB
    private ForumReadService forumReadService;
    
    @EJB
    private LoginService loginService;
    
    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private SubcategoryBean subcategoryBean;
    
    @EJB
    private TopicBean topicBean;
    
    @EJB
    private PostBean postBean;
    
    @EJB
    private MemberBean memberBean;

    @GET
    @Path("{subcategoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSubcategory(@PathParam("subcategoryId") int subcategoryId) {
    	return getSubcategoryWithPage(subcategoryId, 1);
    }
    
    @GET
    @Path("{subcategoryId}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSubcategoryWithPage(@PathParam("subcategoryId") int subcategoryId, @PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
        JSONArray topicJsonArray = new JSONArray();

        Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
        List<Topic> topics = topicBean.findEntitiesByEquality(Topic_.subcategoryId, subcategoryId);

        for (Topic topic : topics) {
            JSONObject topicJson = new JSONObject();

            boolean isUnread = forumReadService.hasTopicUnreadPostsByMember(topic, loginService.getCurrentMember());
            Post firstPost = forumReadService.getFirstPostFromTopic(topic);
            Member starterMemberPosted = firstPost.getMember();
            Post lastPost = forumReadService.getLastPostFromTopic(topic);
            Member lastMemberPosted = lastPost.getMember();

            topicJson.put("unread", isUnread);
            topicJson.put("title", topic.getTitle());
            topicJson.put("starter", starterMemberPosted.getDisplayName());
            topicJson.put("startDate", Formatter.formatTimeStamp(firstPost.getTime()));
            topicJson.put("postCount", topic.getPosts().size());
            topicJson.put("viewCount", topic.getViewCount());
            topicJson.put("lastPoster", lastMemberPosted.getDisplayName());
            topicJson.put("lastDate", Formatter.formatTimeStamp(lastPost.getTime()));
            topicJson.put("topicLink", "#/topic/" + topic.getId());
            topicJson.put("postLink", "#/topic/" + topic.getId() + "/" + lastPost.getPostNumber());
            topicJson.put("starterLink", "#/user/" + starterMemberPosted.getId());
            topicJson.put("posterLink", "#/user/" + lastMemberPosted.getId());

            topicJsonArray.put(topicJson);
        }

        result.put("title", subcategory.getTitle());
        result.put("topics", topicJsonArray);
        result.put("pages", NavigationUtils.getPagesJsonArray("#/subcategory/" + subcategoryId, pageNumber, subcategory.getTopics().size()));
        return result.toString();
    }
}
