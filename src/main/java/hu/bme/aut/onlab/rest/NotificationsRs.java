package hu.bme.aut.onlab.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import hu.bme.aut.onlab.bean.ForumReadService;
import hu.bme.aut.onlab.bean.LoginService;
import hu.bme.aut.onlab.bean.dao.NotificationBean;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Notification;
import hu.bme.aut.onlab.model.NotificationEvent;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.NavigationUtils;
import hu.bme.aut.onlab.util.NotificationType;

@Path("/notifications")
public class NotificationsRs {

	@EJB
	private ForumReadService forumReadService;

	@EJB
	private LoginService loginService;
	
	@EJB
	private NotificationBean notificationBean;


	@Path("")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotifications(@Context Member member) {
		return getNotificationsWithPage(member, 1);
	}
	
	@Path("{pageNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotificationsWithPage(@Context Member member, @PathParam("pageNumber") int pageNumber) {
		JSONObject result = new JSONObject();

		if (member != null) {
			Collection<Notification> notifications = member.getNotifications();
			JSONArray notificationsJsonArray = new JSONArray();
			for (Notification notification : notifications) {
				JSONObject notificationJson = new JSONObject();
				NotificationEvent notificationEvent = notification.getNotificationEvent();
				notificationJson.put("type", NotificationType.getNotificationType(notificationEvent.getType()).getString());
				notificationJson.put("text", notificationEvent.getText());
				notificationJson.put("time", Formatter.formatTimeStampForMessage(notificationEvent.getTime()));
				notificationJson.put("link", notificationEvent.getLink());
				notificationJson.put("unread", notification.isSeen());
				notificationsJsonArray.put(notificationJson);
			}
			result.put("notifications", notificationsJsonArray);
			result.put("pages", NavigationUtils.getPagesJsonArray("#/notifications", pageNumber, notifications.size()));
		}
		
		return result.toString();

	}

}
