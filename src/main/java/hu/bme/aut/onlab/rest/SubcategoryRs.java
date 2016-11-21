package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.bean.ForumReadService;
import hu.bme.aut.onlab.bean.LoginService;
import hu.bme.aut.onlab.bean.NotificationService;
import hu.bme.aut.onlab.bean.dao.*;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;
import hu.bme.aut.onlab.util.NotificationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Path("/category")
public class SubcategoryRs {

    @EJB
    private ForumReadService forumReadService;
    
    @EJB 
    private NotificationService notificationService;
    
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

    @EJB
    private SubcategorySubscriptionBean subcategorySubscriptionBean;

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
            result.put("isFollowedByMember", forumReadService.isMemberFollowingSubcategory(member, subcategory));
	        result.put("topics", topicJsonArray);
	        result.put("pages", NavigationUtils.getPagesJsonArray("#/category/" + subcategoryId, pageNumber, subcategory.getTopics().size()));
        }
        
        return result.toString();
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addTopic(@Context Member member, String data) {
        JSONObject input = new JSONObject(data);
        JSONObject result = new JSONObject();
        String errorMessage;

        if (member != null) {
            int subcategoryId = Integer.parseInt((String) input.get("category"));
            Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
            if (subcategory != null) {
                String subcategoryTitle = (String) input.get("title");
                String postText = (String) input.get("text");

                Topic topic = new Topic();
                topic.setTitle(subcategoryTitle);
                topic.setSubcategory(subcategory);
                topic.setViewCount(0);
                topicBean.add(topic);

                Post post = new Post();
                post.setTopic(topic);
                post.setMember(member);
                post.setText(postText);
                post.setPostNumber(1);
                post.setTime(Timestamp.valueOf(LocalDateTime.now()));
                postBean.add(post);

                member.setPostCount(member.getPostCount() + 1);
                member.setTopicCount(member.getTopicCount() + 1);
                memberBean.merge(member);

                topicBean.flush();
                
                //add mention notification
				if (postText.contains("@")) {
					String mentionedName = NotificationUtils.getMentionedName(postText);
					if (mentionedName != null) {
						List<Member> list = memberBean.findEntitiesByEquality(Member_.displayName, mentionedName);
						if (list.size() > 0) {
							Member mentionedMember = list.get(0);
							notificationService.addMention(member, mentionedMember, post);
						}
					}
				}

                forumReadService.renewTopicSeenByMember(member, topic);

                result.put("success", true);
                result.put("topic", topic.getId());
                return result.toString();
            }
            else {
                errorMessage = "Unknown category.";
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
            int subcategoryId = Integer.parseInt((String) input.get("category"));
            Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
            if (subcategory != null) {
                if (input.has("isFollowRequest")) {

                    boolean isFollowRequest = input.getBoolean("isFollowRequest");
                    SubcategorySubscription existingSubcategorySubscription = forumReadService.getSubcategorySubscription(member, subcategory);

                    if (isFollowRequest) {
                        // Request to follow the subcategory
                        if (existingSubcategorySubscription == null) {
                            SubcategorySubscription subcategorySubscription = new SubcategorySubscription();
                            subcategorySubscription.setMember(member);
                            subcategorySubscription.setSubcategory(subcategory);
                            subcategorySubscriptionBean.add(subcategorySubscription);
                        }

                    } else {
                        // Request to unfollow the subcategory
                        if (existingSubcategorySubscription != null) {
                            subcategorySubscriptionBean.remove(existingSubcategorySubscription);
                        }
                    }

                    result.put("success", true);
                    return result.toString();
                } else {
                    errorMessage = "Unknown error has occurred.";
                }
            } else {
                errorMessage = "Unknown category.";
            }
        } else {
            errorMessage = "Unidentified member.\nPlease log in.";
        }

        result.put("success", false);
        result.put("errorMessage", errorMessage);
        return result.toString();
    }
}
