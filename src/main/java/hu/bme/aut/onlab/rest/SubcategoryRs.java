package hu.bme.aut.onlab.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.bme.aut.onlab.beans.ForumReadService;
import hu.bme.aut.onlab.beans.LoginService;
import hu.bme.aut.onlab.beans.dao.CategoryBean;
import hu.bme.aut.onlab.beans.dao.MemberBean;
import hu.bme.aut.onlab.beans.dao.PostBean;
import hu.bme.aut.onlab.beans.dao.SubcategoryBean;
import hu.bme.aut.onlab.beans.dao.TopicBean;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.model.Topic_;

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
    public String listRemoteCombinedSubcategory(@PathParam("subcategoryId") int subcategoryId) {
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
            topicJson.put("startDate", firstPost.getTime());
            topicJson.put("postCount", topic.getPosts().size());
            // TODO: missing from model?
            topicJson.put("viewCount", 9);
            topicJson.put("lastPoster", lastMemberPosted.getDisplayName());
            topicJson.put("lastDate", lastPost.getTime());
            topicJson.put("topicLink", "#/topic/" + topic.getId());
            topicJson.put("postLink", "#/topic/" + topic.getId() + "/" + lastPost.getPostNumber());
            topicJson.put("starterLink", "#/user/" + starterMemberPosted.getId());
            topicJson.put("posterLink", "#/user/" + lastMemberPosted.getId());

            topicJsonArray.put(topicJson);
        }


        result.put("title", subcategory.getTitle());
        result.put("topics", topicJsonArray);

        return result.toString();
    }
}
