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
import hu.bme.aut.onlab.model.Message;
import hu.bme.aut.onlab.util.Formatter;
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
			
			Message firstMessage = messagingService.getMessageOfConversation(conversation, 1);
			Message lastMessage = messagingService.getMessageOfConversation(conversation, conversation.getMessageCount());
			
			conversationJson.put("unread", messagingService.isConversationUnread(conversation, member));
			conversationJson.put("title", conversation.getTitle());
			conversationJson.put("starter", firstMessage.getMember().getDisplayName());
			conversationJson.put("startDate", Formatter.formatTimeStampForMessage(firstMessage.getTime()));
			conversationJson.put("messages", conversation.getMessageCount());
			conversationJson.put("lastPoster", lastMessage.getMember().getDisplayName());
			conversationJson.put("lastDate", Formatter.formatTimeStampForMessage(lastMessage.getTime()));
			conversationJson.put("conversationLink", "#/messages/" + conversation.getConversationNumber());
			conversationJson.put("lastMessageLink", "#/messages/" + conversation.getConversationNumber() + "/" + NavigationUtils.getPageOfElement(lastMessage.getMessageNumber()));
			conversationJson.put("lastPosterLink", "#/user/" + lastMessage.getMember().getId());
			conversationJson.put("starterLink", "#/user/" + firstMessage.getMember().getId());
			conversationJson.put("starerImageLink", firstMessage.getMember().getPictureId());
			conversationJson.put("lastPosterImageLink", lastMessage.getMember().getPictureId());
			
			conversationsJsonArray.put(conversationJson);
		}
		
		result.put("pages", 
				NavigationUtils.getPagesJsonArray(
						"#/conversations", 
						pageNumber, 
						messagingService.getConversationsCount(member)));
		
		return result.toString();
	}
	
	
	
}
