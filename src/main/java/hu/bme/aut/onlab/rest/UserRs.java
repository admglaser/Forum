package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.MemberBean;
import hu.bme.aut.onlab.beans.MemberGroupBean;
import hu.bme.aut.onlab.beans.PostBean;
import hu.bme.aut.onlab.beans.TopicBean;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.model.Member_;
import hu.bme.aut.onlab.model.Post_;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by N. Vilagos.
 */
@Path("/user")
public class UserRs {

    @EJB
    private MemberBean memberBean;

    @EJB
    private TopicBean topicBean;

    @EJB
    private PostBean postBean;

    @EJB
    private MemberGroupBean memberGroupBean;

    private JSONObject generateBase(Member member) {
        JSONObject result = new JSONObject();

        result.put("id", member.getId());
        result.put("name", member.getDisplayName());
        result.put("joined", member.getRegisterTime());
        // TODO: missing from model?
        result.put("active", member.getRegisterTime());
        // TODO: implement image working
        result.put("imageLink", "");

        return result;
    }

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedUserOverview(@PathParam("userId") int userId) {
        Member member = memberBean.findEntityById(Member_.id, userId);

        JSONObject result = generateBase(member);

        List<Topic> createdTopics  = topicBean.getCreatedTopicByMember(userId);
        MemberGroup memberGroup = member.getMemberGroupByMemberGroupId();

        result.put("memberGroup", memberGroup.getTitle());
        result.put("topics", createdTopics.size());
        /* TODO: Discuss 2 options:
            1. member.getPostCount();
            2. postBean.findEntitiesByEquality(Post_.memberId, userId).size();
          */
        result.put("posts", member.getPostCount());
        result.put("views", member.getProfileViewsCount());
        result.put("birthday", member.getBirthday());
        result.put("email", member.getEmail());

        return result.toString();
    }

    @GET
    @Path("{userId}/likes")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedUserLikes(@PathParam("userId") int userId) {
        Member member = memberBean.findEntityById(Member_.id, userId);
        JSONObject result = generateBase(member);
        JSONArray likedPostsJsonArray = new JSONArray();

        List<Post> likedPosts = postBean.getLikedPostsOfMember(userId);
        for (Post likedPost : likedPosts) {
            JSONObject likedPostJsonObject = new JSONObject();

            Topic topic = likedPost.getTopicByTopicId();

            likedPostJsonObject.put("title", topic.getTitle());
            likedPostJsonObject.put("link", "#/topic/" + topic.getId() + "/" + likedPost.getPostNumber());
            likedPostJsonObject.put("date", likedPost.getTime());
            likedPostJsonObject.put("text", likedPost.getText());

            likedPostsJsonArray.put(likedPostJsonObject);
        }

        result.put("posts", likedPostsJsonArray);
        return result.toString();
    }

    @GET
    @Path("{userId}/topics")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedUserTopics(@PathParam("userId") int userId) {
        Member member = memberBean.findEntityById(Member_.id, userId);
        JSONObject result = generateBase(member);
        JSONArray topicsJsonArray = new JSONArray();

        List<Topic> createdTopics  = topicBean.getCreatedTopicByMember(userId);
        for (Topic createdTopic : createdTopics) {
            JSONObject createdTopicJson = new JSONObject();
            Post firstPost = postBean.getFirstPostFromTopic(createdTopic.getId());

            createdTopicJson.put("title", createdTopic.getTitle());
            createdTopicJson.put("link", "#/topic/" + createdTopic.getId());
            createdTopicJson.put("date", firstPost.getTime());
            createdTopicJson.put("text", firstPost.getText());

            topicsJsonArray.put(createdTopicJson);
        }

        result.put("topics", topicsJsonArray);
        return result.toString();
    }

    @GET
    @Path("{userId}/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRemoteCombinedUserPosts(@PathParam("userId") int userId) {
        Member member = memberBean.findEntityById(Member_.id, userId);
        JSONObject result = generateBase(member);
        JSONArray createdPostsJsonArray = new JSONArray();

        List<Post> createdPosts  = postBean.findEntitiesByEquality(Post_.memberId, userId);
        for (Post createdPost : createdPosts) {
            JSONObject createdPostJson = new JSONObject();

            Topic topic = createdPost.getTopicByTopicId();

            createdPostJson.put("title", topic.getTitle());
            createdPostJson.put("link", "#/topic/" + topic.getId() + "/" + createdPost.getPostNumber());
            createdPostJson.put("date", createdPost.getTime());
            createdPostJson.put("text", createdPost.getText());

            createdPostsJsonArray.put(createdPostJson);
        }


        result.put("posts", createdPostsJsonArray);
        return result.toString();
    }
}
