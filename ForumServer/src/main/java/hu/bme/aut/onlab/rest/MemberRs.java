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

import hu.bme.aut.onlab.dao.ForumDao;
import hu.bme.aut.onlab.dao.LoginDao;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;

@Path("/members")
public class MemberRs {

    @EJB
    private ForumDao forumReadService;

    @EJB
    private LoginDao loginService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMembers() {
        return getMembersWithPage(1);
    }

    @GET
    @Path("{pageNumber}")
    public String getMembersWithPage(@PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
        JSONArray memberJsonArray = new JSONArray();

        List<Member> members = forumReadService.getMembersOnPage(pageNumber);

        for (Member member : members) {
            JSONObject memberJson = new JSONObject();

            int memberId = member.getId();
            MemberGroup memberGroup = member.getMemberGroup();
            String memberGroupTitle = "";
            if (memberGroup != null) {
                memberGroupTitle = memberGroup.getTitle();
            }

            memberJson.put("id", memberId);
            memberJson.put("name", member.getDisplayName());
            memberJson.put("joined", Formatter.formatTimeStamp(member.getRegisterTime()));
            memberJson.put("memberGroup", memberGroupTitle);
            memberJson.put("postCount", member.getPostCount());
            memberJson.put("topicCount", member.getTopicCount());
            memberJson.put("userLink", "#/user/" + memberId);
            memberJson.put("userImageLink", LinkUtils.getProfilePictureLink(member.getPictureId()));
            memberJson.put("imageLink", member.getPictureId());
            memberJson.put("style", Formatter.getMemberStyle(member));

            memberJsonArray.put(memberJson);
        }

        result.put("members", memberJsonArray);
        result.put("pages", NavigationUtils.getPagesJsonArray("#/members", pageNumber, forumReadService.getMembersCount()));

        return result.toString();
    }

}
