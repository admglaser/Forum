package hu.bme.aut.onlab.rest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.bme.aut.onlab.dao.ForumDao;
import hu.bme.aut.onlab.dao.LoginDao;
import hu.bme.aut.onlab.dao.MessagingDao;
import hu.bme.aut.onlab.dao.model.ConversationDao;
import hu.bme.aut.onlab.dao.model.MessageDao;
import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.model.Message;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;

@Path("message")
public class MessageRs {

    @EJB
    private ForumDao forumReadService;

    @EJB
    private MessagingDao messagingService;

    @EJB
    private LoginDao loginService;

    @EJB
    private ConversationDao conversationBean;

    @EJB
    private MessageDao messageBean;

    @GET
    @Path("{conversationNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessages(@Context Member member, @PathParam("conversationNumber") int conversationNumber) {
        return getMessagesWithPage(member, conversationNumber, 1);
    }

    @GET
    @Path("{conversationNumber}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessagesWithPage(@Context Member member, @PathParam("conversationNumber") int conversationNumber, @PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
        JSONArray messagesJsonArray = new JSONArray();

        if (member != null) {
        	Conversation conversation = messagingService.getConversation(member, conversationNumber);
        	if (conversation != null) {
        		List<Message> messages = messagingService.getMessagesOfConversationOnPage(conversation, pageNumber);
        		// messages is empty if navigated to a too high page number
        		if (!messages.isEmpty()) {
        			Message firstMessage = messages.get(0);
        			
        			for (Message message : messages) {
        				JSONObject messageJson = new JSONObject();
        				
        				Member member2 = message.getMember();
        				MemberGroup memberGroup = member2.getMemberGroup();
        				
        				messageJson.put("userName", member2.getDisplayName());
        				messageJson.put("userLink", "#/user/" + member2.getId());
        				messageJson.put("userImageLink", LinkUtils.getProfilePictureLink(member2.getPictureId()));
        				messageJson.put("posts", member2.getPostCount());
        				messageJson.put("memberGroup", memberGroup.getTitle());
        				messageJson.put("time", Formatter.formatTimeStampForMessage(message.getTime()));
        				messageJson.put("text", message.getText());
        				messageJson.put("messageNumber", message.getMessageNumber());
        				messageJson.put("style", Formatter.getMemberStyle(member2));
        				messagesJsonArray.put(messageJson);
        			}
        			
        			List<Member> members = conversation.getMembers();
        			StringBuilder sb = new StringBuilder();
        			for (int i=0; i<members.size(); i++) {
        				sb.append(members.get(i).getDisplayName());
        				if (i < members.size()-1) {
        					sb.append(", ");
        				}
        			}
        			result.put("recipients", sb.toString());
        			
        			result.put("conversationNumber", messagingService.getConversationNumber(conversation, member));
        			result.put("title", conversation.getTitle());
        			result.put("starter", firstMessage.getMember().getDisplayName());
        			result.put("startDate", Formatter.formatTimeStamp(firstMessage.getTime()));
        			result.put("messages", messagesJsonArray);
        			result.put("pages", NavigationUtils.getPagesJsonArray("#/message/" + conversation.getId(), pageNumber, messagingService.getMessagesCountOfConversation(conversation)));
        		
        			//update read number
        			Message lastMessage = messages.get(messages.size()-1);
        			messagingService.updateConversationReadMessageNumber(conversation, member, lastMessage.getMessageNumber());
        				
        		}
        	} else {
    			result.put("error", true);
    		}
        } else {
			result.put("error", true);
		}
        
        return result.toString();
    }
    
    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createMessage(@Context Member member, String data) {
    	JSONObject json = new JSONObject(data);
    	JSONObject result = new JSONObject();
    	if (member != null) {
    		String text = json.getString("text");
    		int conversationNumber = Integer.parseInt((String) json.get("conversationNumber"));
    		Conversation conversation = messagingService.getConversation(member, conversationNumber);
    		if (conversation != null) {
    			Message message = new Message();
    			message.setConversation(conversation);
    			message.setMember(member);
    			message.setText(text);
    			message.setTime(Timestamp.valueOf(LocalDateTime.now()));
    			int messageCount = conversation.getMessageCount();
    			messageCount++;
				message.setMessageNumber(messageCount);
				conversation.setMessageCount(messageCount);
				messageBean.add(message);
				conversationBean.merge(conversation);
				messagingService.updateConversationReadMessageNumber(conversation, member, messageCount);
				result.put("success", true);
				return result.toString();
    		}
    	}    	
    	
    	result.put("success", false);
    	return result.toString();
    }
}
