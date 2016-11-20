package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.bean.ForumReadService;
import hu.bme.aut.onlab.bean.dao.MemberBean;
import hu.bme.aut.onlab.bean.dao.PostBean;
import hu.bme.aut.onlab.bean.dao.TopicBean;
import hu.bme.aut.onlab.bean.dao.TopicSubscriptionBean;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Path("topic")
public class TopicRs {

    @EJB
    private ForumReadService forumReadService;

    @EJB
    private TopicBean topicBean;

	@EJB
	private PostBean postBean;

	@EJB
	private MemberBean memberBean;

	@EJB
	private TopicSubscriptionBean topicSubscriptionBean;

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
					result.put("isFollowedByMember", forumReadService.isMemberFollowingTopic(member, topic));
	                result.put("posts", postsJsonArray);
	                result.put("pages", NavigationUtils.getPagesJsonArray("#/topic/" + topic.getId(), pageNumber, forumReadService.getPostsCountOfTopic(topic)));
	            }
        	}
        }
        return result.toString();
    }


	@POST
	@Path("new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addPost(@Context Member member, String data) {
		JSONObject input = new JSONObject(data);
		JSONObject result = new JSONObject();
		String errorMessage;

		if (member != null) {
			int topicId = Integer.parseInt((String) input.get("topic"));
			Topic topic = topicBean.findEntityById( topicId );
			if (topic != null) {
				String quotedPostText = (input.has("quote")) ? (String) input.get("quote") : null;
				String postText = (String) input.get("text");
				Post lastPostInTopic = forumReadService.getLastPostFromTopic(topic);

				Post post = new Post();
				post.setTopic(topic);
				post.setMember(member);
				post.setText( (quotedPostText==null) ? postText : quotedPostText + postText);
				post.setPostNumber(lastPostInTopic.getPostNumber() + 1);
				post.setTime(Timestamp.valueOf(LocalDateTime.now()));

				postBean.add(post);

				member.setPostCount(member.getPostCount() + 1);
				memberBean.merge(member);

				result.put("success", true);
				return result.toString();
			}
			else {
				errorMessage = "Unknown topic.";
			}
		} else {
			errorMessage = "Unidentified member.\nPlease log in.";
		}

		result.put("success", false);
		result.put("errorMessage", errorMessage);
		return result.toString();
	}

	@POST
	@Path("follow")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String followTopic(@Context Member member, String data) {
		JSONObject input = new JSONObject(data);
		JSONObject result = new JSONObject();
		String errorMessage;

		if (member != null) {
			int topicId = Integer.parseInt((String) input.get("topic"));
			Topic topic = topicBean.findEntityById( topicId );
			if (topic != null) {
				if (input.has("isFollowRequest")) {

					boolean isFollowRequest = input.getBoolean("isFollowRequest");
					TopicSubscription existingTopicSubscription = forumReadService.getTopicSubscription(member, topic);

					if (isFollowRequest) {
						// Request to follow the topic
						if (existingTopicSubscription == null) {
							TopicSubscription topicSubscription = new TopicSubscription();
							topicSubscription.setMember(member);
							topicSubscription.setTopic(topic);
							topicSubscriptionBean.add(topicSubscription);
						}

					} else {
						// Request to unfollow the topic
						if (existingTopicSubscription != null) {
							topicSubscriptionBean.remove(existingTopicSubscription);
						}
					}

					result.put("success", true);
					return result.toString();
				} else {
					errorMessage = "Unknown error has occurred.";
				}
			} else {
				errorMessage = "Unknown topic.";
			}
		} else {
			errorMessage = "Unidentified member.\nPlease log in.";
		}

		result.put("success", false);
		result.put("errorMessage", errorMessage);
		return result.toString();
	}
}
