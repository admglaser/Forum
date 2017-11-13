package hu.bme.aut.onlab.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.bme.aut.onlab.dao.MessagingDao;
import hu.bme.aut.onlab.dao.NotificationsDao;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Message;
import hu.bme.aut.onlab.model.Notification;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.NavigationUtils;
import hu.bme.aut.onlab.util.NotificationType;

@Path("/navbar")
public class NavbarRs  {
   
    @EJB
    private MessagingDao messagingService; 
    
    @EJB
    private NotificationsDao notificationService; 
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getNavbar(@Context Member member) {
    	JSONObject result = new JSONObject();
    	result.put("loggedIn", member!=null);
    	
    	if (member != null) {
    		result.put("username", member.getDisplayName());
    		result.put("userLink", "#/user/" + member.getId());
    		
    		JSONArray messageJsonArray = new JSONArray();
    		List<Message> messages = messagingService.getLastFiveMessages(member);
    		boolean hasUnreadMessage = false;
    		for (Message message : messages) {
    			JSONObject messageJson = new JSONObject();
    			messageJson.put("title", message.getConversation().getTitle());
    			messageJson.put("sender", message.getMember().getDisplayName());
    			messageJson.put("time", Formatter.formatTimeStampForMessage(message.getTime()));
    			
    			int pageNumber = NavigationUtils.getPageOfElement(message.getMessageNumber());
    			messageJson.put("link", "#/message/" + messagingService.getConversationNumber(message.getConversation(), member) + (pageNumber == 1 ? "" : "/" + pageNumber));
    			
    			boolean conversationUnread = messagingService.isConversationUnread(message.getConversation(), member);
				if (!hasUnreadMessage) {
					if (conversationUnread) {
						hasUnreadMessage = true;
					}
				}
    			messageJson.put("unread", conversationUnread);
    			messageJsonArray.put(messageJson);
    		}
    		result.put("messagesUnread", hasUnreadMessage);
    		result.put("messages", messageJsonArray);
    		
    		JSONArray notificationsJsonArray = new JSONArray();
    		List<Notification> notifications = notificationService.getLastFiveNotifications(member);
    		boolean hasUnreadNotification = false;
			for (Notification notification : notifications) {
				JSONObject notificationJson = new JSONObject();
				notificationJson.put("type", NotificationType.getNotificationType(notification.getNotificationEvent().getType()));
				notificationJson.put("text", notification.getNotificationEvent().getText());
				notificationJson.put("time", Formatter.formatTimeStampForMessage(notification.getNotificationEvent().getTime()));
				notificationJson.put("link", notification.getNotificationEvent().getLink());
				notificationJson.put("id", notification.getId());
				
				boolean notificationUnread = notificationService.isNotificationUnread(notification);
				if (!hasUnreadNotification) {
					if (notificationUnread) {
						hasUnreadNotification = true;
					}
				}
				notificationJson.put("unread", notificationUnread);
				notificationsJsonArray.put(notificationJson);
			}
			result.put("notificationsUnread", hasUnreadNotification);
			result.put("notifications", notificationsJsonArray);
			
    	}
    	
    	return result.toString();
    }
}
