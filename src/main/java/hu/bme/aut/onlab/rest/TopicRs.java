package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.ForumReadService;
import hu.bme.aut.onlab.beans.dao.TopicBean;
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

@Path("topic")
public class TopicRs {

    @EJB
    private ForumReadService forumReadService;

    @EJB
    private TopicBean topicBean;

    @GET
    @Path("{topicId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopic(@PathParam("topicId") int topicId) {
        return getTopicWithPage(topicId, 1);
    }

    @GET
    @Path("{topicId}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopicWithPage(@PathParam("topicId") int topicId, @PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
        JSONArray postsJsonArray = new JSONArray();

        Topic topic = topicBean.findEntityById(topicId);

        if (topic != null) {
            List<Post> posts = forumReadService.getPostsOfTopicOnPage(topic, pageNumber);
            // posts is empty if navigated to a too high page number
            if (! posts.isEmpty() ) {
                Post firstPost = posts.get(0);

                for (Post post : posts) {
                    JSONObject postJson = new JSONObject();

                    Member member = post.getMember();
                    MemberGroup memberGroup = member.getMemberGroup();

                    postJson.put("username", member.getDisplayName());
                    postJson.put("userLink", "#/user/" + member.getId());
                    postJson.put("userImageLink", member.getPictureId());
                    postJson.put("postCount", member.getPostCount());
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
        return result.toString();
    }
}
