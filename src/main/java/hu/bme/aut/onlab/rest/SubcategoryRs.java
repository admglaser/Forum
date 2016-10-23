package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.MemberBean;
import hu.bme.aut.onlab.beans.PostBean;
import hu.bme.aut.onlab.beans.SubcategoryBean;
import hu.bme.aut.onlab.beans.TopicBean;
import hu.bme.aut.onlab.model.Post;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Subcategory;
import hu.bme.aut.onlab.model.Subcategory_;
import hu.bme.aut.onlab.model.Topic;
import hu.bme.aut.onlab.model.Topic_;
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
@Path("/subcategory")
public class SubcategoryRs {
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

        Subcategory subcategory = subcategoryBean.findEntityById(Subcategory_.categoryId, subcategoryId);
        List<Topic> topics = topicBean.findEntitiesByEquality(Topic_.subcategoryId, subcategoryId);

        for (Topic topic : topics) {
            JSONObject topicJson = new JSONObject();

            int topicId = topic.getId();
            int memberId = memberBean.getCurrentMember().getId();
            boolean isUnread = topicBean.hasUnreadPosts(topicId, memberId);
            Post firstPost = postBean.getFirstPostFromTopic(topicId);
            Member starterMemberPosted = firstPost.getMemberByMemberId();
            Post lastPost = postBean.getLastPostFromTopic(topicId);
            Member lastMemberPosted = lastPost.getMemberByMemberId();

            topicJson.put("unread", isUnread);
            topicJson.put("title", topic.getTitle());
            topicJson.put("starter", starterMemberPosted.getDisplayName());
            topicJson.put("startDate", firstPost.getTime());
            topicJson.put("postCount", topic.getPostsById().size());
            // TODO: missing from model?
            topicJson.put("viewCount", 9);
            topicJson.put("lastPoster", lastMemberPosted.getDisplayName());
            topicJson.put("lastDate", lastPost.getTime());
            topicJson.put("topicLink", "#/topic/" + topicId);
            topicJson.put("postLink", "#/topic/" + topicId + "/" + lastPost.getPostNumber());
            topicJson.put("starterLink", "#/user/" + starterMemberPosted.getId());
            topicJson.put("posterLink", "#/user/" + lastMemberPosted.getId());

            topicJsonArray.put(topicJson);
        }


        result.put("title", subcategory.getTitle());
        result.put("topics", topicJsonArray);

        return result.toString();
    }
}
