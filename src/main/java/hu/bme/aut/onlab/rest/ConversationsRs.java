package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.bean.LoginService;
import hu.bme.aut.onlab.bean.MessagingService;
import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Message;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.NavigationUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/conversations")
public class ConversationsRs {

	@EJB
	private MessagingService messagingService;

	@EJB
	private LoginService loginService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getConversations(@Context Member member) {
		return getConversationsWithPage(member, 1);
	}
	
	@GET
	@Path("{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getConversationsWithPage(@Context Member member, @PathParam("pageNumber") int pageNumber) {
		JSONObject result = new JSONObject();
		
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
		result.put("conversations", conversationsJsonArray);
		result.put("pages", 
				NavigationUtils.getPagesJsonArray(
						"#/conversations", 
						pageNumber, 
						messagingService.getConversationsCount(member)));
		
		return result.toString();
	}
	
	
	
}
