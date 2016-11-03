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

import hu.bme.aut.onlab.beans.LoginService;
import hu.bme.aut.onlab.beans.MessagingService;
import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.util.NavigationUtils;

@Path("/conversations")
public class ConversationsRs {

	@EJB
	private MessagingService messagingService;

	@EJB
	private LoginService loginService;
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String getConversations() {
		return getConversationsWithPage(1);
	}
	
	@GET
	@Path("{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getConversationsWithPage(@PathParam("pageNumber") int pageNumber) {
		JSONObject result = new JSONObject();
		Member member = loginService.getCurrentMember();
		
		JSONArray conversationsJsonArray = new JSONArray();
		List<Conversation> conversations = messagingService.getConversations(member, pageNumber);
		for (Conversation conversation : conversations) {
			JSONObject conversationJson = new JSONObject();
			conversationJson.put("unread", );
			conversationJson.put("title", );
			conversationJson.put("starter", );
			conversationJson.put("startDate", );
			conversationJson.put("messages", );
			conversationJson.put("lastPoster", );
			conversationJson.put("lastDate", );
			conversationJson.put("conversationLink", );
			conversationJson.put("lastMessageLink", );
			conversationJson.put("lastPosterLink", );
			conversationJson.put("starterLink", );
			conversationJson.put("starerImageLink", );
			conversationJson.put("lastPosterImageLink", );
			
			conversationsJsonArray.put(conversationJson);
		}
		
		result.put("pages", NavigationUtils.getPagesJsonArray(
				"#/conversations", 
				pageNumber, 
				messagingService.getConversationsCount(member)));
		
		return result.toString();
	}
	
	
	
}
