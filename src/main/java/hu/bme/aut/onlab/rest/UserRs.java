package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.bean.ForumReadService;
import hu.bme.aut.onlab.bean.LoginService;
import hu.bme.aut.onlab.bean.dao.*;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Topic;
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
import java.util.List;

@Path("/user")
public class UserRs {

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

	private JSONObject generateBase(Member member) {
		JSONObject result = new JSONObject();

		result.put("id", member.getId());
		result.put("name", member.getDisplayName());
		result.put("joined", member.getRegisterTime());
		result.put("active", Formatter.formatTimeStamp(member.getActiveTime()));
		result.put("imageLink", member.getPictureId());

		return result;
	}

	@GET
	@Path("{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserOverview(@PathParam("userId") int userId) {
		Member member = memberBean.findEntityById(userId);
		JSONObject result = generateBase(member);

		MemberGroup memberGroup = member.getMemberGroup();

		result.put("memberGroup", memberGroup.getTitle());
		result.put("topicCount", member.getTopicCount());
		result.put("postCount", member.getPostCount());
		result.put("viewCount", member.getViewsCount());
		result.put("birthday", member.getBirthday());
		result.put("email", member.getEmail());

		return result.toString();
	}

	@GET
	@Path("{userId}/likes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLikes(@PathParam("userId") int userId) {
		return getUserLikesWithPage(userId, 1);
	}
	
	@GET
	@Path("{userId}/likes/{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserLikesWithPage(@PathParam("userId") int userId, @PathParam("pageNumber") int pageNumber) {
		Member member = memberBean.findEntityById(userId);
		JSONObject result = generateBase(member);
		JSONArray likedPostsJsonArray = new JSONArray();

		List<Post> likedPosts = forumReadService.getLikedPostsOfMember(member, pageNumber);
		for (Post likedPost : likedPosts) {
			JSONObject likedPostJsonObject = new JSONObject();

			Topic topic = likedPost.getTopic();

			likedPostJsonObject.put("title", topic.getTitle());
			likedPostJsonObject.put("link", "#/topic/" + topic.getId() + "/" + likedPost.getPostNumber());
			likedPostJsonObject.put("date", Formatter.formatTimeStamp(likedPost.getTime()));
			likedPostJsonObject.put("text", likedPost.getText());
			likedPostJsonObject.put("likeCount", forumReadService.getPostLikesCount(likedPost));

			likedPostsJsonArray.put(likedPostJsonObject);
		}

		result.put("posts", likedPostsJsonArray);
		result.put("pages", NavigationUtils.getPagesJsonArray("#/user/" + userId + "/likes", pageNumber, forumReadService.getLikedPostsCountOfMember(member)));
		return result.toString();
	}

	@GET
	@Path("{userId}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserTopics(@PathParam("userId") int userId) {
		return getUserTopicsWithPage(userId, 1);
	}
	
	@GET
	@Path("{userId}/topics/{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserTopicsWithPage(@PathParam("userId") int userId, @PathParam("pageNumber") int pageNumber) {
		Member member = memberBean.findEntityById(userId);
		JSONObject result = generateBase(member);
		JSONArray topicsJsonArray = new JSONArray();

		List<Topic> createdTopics = forumReadService.getCreatedTopicsByMember(member, pageNumber);
		for (Topic createdTopic : createdTopics) {
			JSONObject createdTopicJson = new JSONObject();
			Post firstPost = forumReadService.getFirstPostFromTopic(createdTopic);

			createdTopicJson.put("title", createdTopic.getTitle());
			createdTopicJson.put("link", "#/topic/" + createdTopic.getId());
			createdTopicJson.put("date", Formatter.formatTimeStamp(firstPost.getTime()));
			createdTopicJson.put("text", firstPost.getText());
			createdTopicJson.put("likeCount", forumReadService.getPostLikesCount(firstPost));

			topicsJsonArray.put(createdTopicJson);
		}

		result.put("topics", topicsJsonArray);
		result.put("pages", NavigationUtils.getPagesJsonArray("#/user/" + userId + "/topics", pageNumber, member.getTopicCount()));
		return result.toString();
	}
	
	@GET
	@Path("{userId}/posts")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserPosts(@PathParam("userId") int userId) {
		return getUserPostsWithPage(userId, 1);
	}
	
	@GET
	@Path("{userId}/posts/{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserPostsWithPage(@PathParam("userId") int userId, @PathParam("pageNumber") int pageNumber) {
		Member member = memberBean.findEntityById(userId);
		JSONObject result = generateBase(member);
		JSONArray createdPostsJsonArray = new JSONArray();

		List<Post> createdPosts = forumReadService.getPostsByMember(member, pageNumber);
		for (Post createdPost : createdPosts) {
			JSONObject createdPostJson = new JSONObject();

			Topic topic = createdPost.getTopic();

			createdPostJson.put("title", topic.getTitle());
			createdPostJson.put("link", "#/topic/" + topic.getId() + "/" + createdPost.getPostNumber());
			createdPostJson.put("date", Formatter.formatTimeStamp(createdPost.getTime()));
			createdPostJson.put("text", createdPost.getText());
			createdPostJson.put("likeCount", forumReadService.getPostLikesCount(createdPost));

			createdPostsJsonArray.put(createdPostJson);
		}

		result.put("posts", createdPostsJsonArray);
		result.put("pages", NavigationUtils.getPagesJsonArray("#/user/" + userId + "/posts", pageNumber, member.getPostCount()));
		return result.toString();
	}
	
}
