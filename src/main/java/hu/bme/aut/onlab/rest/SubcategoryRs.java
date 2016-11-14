package hu.bme.aut.onlab.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.bme.aut.onlab.bean.ForumReadService;
import hu.bme.aut.onlab.bean.LoginService;
import hu.bme.aut.onlab.bean.dao.CategoryBean;
import hu.bme.aut.onlab.bean.dao.MemberBean;
import hu.bme.aut.onlab.bean.dao.PostBean;
import hu.bme.aut.onlab.bean.dao.SubcategoryBean;
import hu.bme.aut.onlab.bean.dao.TopicBean;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.model.Topic_;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;

@Path("/category")
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
    public String getSubcategory(@Context Member member, @PathParam("subcategoryId") int subcategoryId) {
    	return getSubcategoryWithPage(member, subcategoryId, 1);
    }
    
    @GET
    @Path("{subcategoryId}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSubcategoryWithPage(@Context Member member, @PathParam("subcategoryId") int subcategoryId, @PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
       
        Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
        boolean canMemberViewSubcategory = forumReadService.canMemberViewSubcategory(member, subcategory);
        
        if (canMemberViewSubcategory) {
	        JSONArray topicJsonArray = new JSONArray();
	        List<Topic> topics = topicBean.findEntitiesByEquality(Topic_.subcategoryId, subcategoryId);
	
	        for (Topic topic : topics) {
	            JSONObject topicJson = new JSONObject();
	
	            boolean isUnread = member == null ? false : forumReadService.hasTopicUnreadPostsByMember(topic, member);
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
	            topicJson.put("posterImageLink", LinkUtils.getProfilePictureLink(lastMemberPosted.getPictureId()));
	
	            topicJsonArray.put(topicJson);
	        }
	
	        result.put("title", subcategory.getTitle());
	        result.put("topics", topicJsonArray);
	        result.put("pages", NavigationUtils.getPagesJsonArray("#/category/" + subcategoryId, pageNumber, subcategory.getTopics().size()));
        }
        
        return result.toString();
    }
}
