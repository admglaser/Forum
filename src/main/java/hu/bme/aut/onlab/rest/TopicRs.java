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
import hu.bme.aut.onlab.bean.dao.TopicBean;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;

@Path("topic")
public class TopicRs {

    @EJB
    private ForumReadService forumReadService;

    @EJB
    private TopicBean topicBean;

    @GET
    @Path("{topicId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopic(@Context Member member, @PathParam("topicId") int topicId) {
        return getTopicWithPage(member, topicId, 1);
    }

    @GET
    @Path("{topicId}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopicWithPage(@Context Member member, @PathParam("topicId") int topicId, @PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
        JSONArray postsJsonArray = new JSONArray();

        Topic topic = topicBean.findEntityById(topicId);

        if (topic != null) {
        	if (forumReadService.canMemberViewSubcategory(member, topic.getSubcategory())) {
	            List<Post> posts = forumReadService.getPostsOfTopicOnPage(topic, pageNumber);
	            // posts is empty if navigated to a too high page number
	            if (! posts.isEmpty() ) {
	                Post firstPost = posts.get(0);
	
	                for (Post post : posts) {
	                    JSONObject postJson = new JSONObject();
	
	                    Member memberOfPost = post.getMember();
	                    MemberGroup memberGroup = memberOfPost.getMemberGroup();
	
	                    postJson.put("username", memberOfPost.getDisplayName());
	                    postJson.put("userLink", "#/user/" + memberOfPost.getId());
	                    postJson.put("userImageLink", LinkUtils.getProfilePictureLink(memberOfPost.getPictureId()));
	                    postJson.put("postCount", memberOfPost.getPostCount());
	                    postJson.put("memberGroup", memberGroup.getTitle());
	                    postJson.put("time", Formatter.formatTimeStamp(post.getTime()));
	                    postJson.put("text", post.getText());
	                    postJson.put("likeCount", forumReadService.getPostLikesCount(post));
	                    postJson.put("postNumber", post.getPostNumber());
	                    postJson.put("postLink", "#/topic/" + topic.getId() + "/" + pageNumber);
	                    postsJsonArray.put(postJson);
	                }
	
	                result.put("title", topic.getTitle());
	                result.put("startedByText", firstPost.getMember().getDisplayName() + ", " + Formatter.formatTimeStamp(firstPost.getTime()));
	                result.put("posts", postsJsonArray);
	                result.put("pages", NavigationUtils.getPagesJsonArray("#/topic/" + topic.getId(), pageNumber, forumReadService.getPostsCountOfTopic(topic)));
	            }
        	}
        }
        return result.toString();
    }
}
