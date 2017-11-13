package hu.bme.aut.onlab.rest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import hu.bme.aut.onlab.dao.LoginDao;
import hu.bme.aut.onlab.dao.MessagingDao;
import hu.bme.aut.onlab.dao.model.ConversationDao;
import hu.bme.aut.onlab.dao.model.ConversationSeenByMemberDao;
import hu.bme.aut.onlab.dao.model.ConversationToMemberDao;
import hu.bme.aut.onlab.dao.model.MemberDao;
import hu.bme.aut.onlab.dao.model.MessageDao;
import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.ConversationSeenByMember;
import hu.bme.aut.onlab.model.ConversationToMember;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Member_;
import hu.bme.aut.onlab.model.Message;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;

@Path("/conversations")
public class ConversationsRs {

	@EJB
	private MessagingDao messagingService;

	@EJB
	private LoginDao loginService;

	@EJB
	MemberDao memberBean;

	@EJB
	MessageDao messageBean;

	@EJB
	ConversationDao conversationBean;

	@EJB
	ConversationSeenByMemberDao conversationSeenByMemberBean;
	
	@EJB
	ConversationToMemberDao conversationToMemberBean;

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

		if (member != null) {
			JSONArray conversationsJsonArray = new JSONArray();
			List<Conversation> conversations = messagingService.getConversationsOnPage(member, pageNumber);
			for (Conversation conversation : conversations) {
				JSONObject conversationJson = new JSONObject();

				Message firstMessage = messagingService.getMessageOfConversation(conversation, 1);
				Message lastMessage = messagingService.getMessageOfConversation(conversation,
						conversation.getMessageCount());
				Member firstMessageMember = firstMessage.getMember();
				Member lastMessageMemmber = lastMessage.getMember();

				conversationJson.put("unread", messagingService.isConversationUnread(conversation, member));
				conversationJson.put("title", conversation.getTitle());
				conversationJson.put("starter", firstMessageMember.getDisplayName());
				conversationJson.put("startDate", Formatter.formatTimeStampForMessage(firstMessage.getTime()));
				conversationJson.put("messages", conversation.getMessageCount());
				conversationJson.put("lastPoster", lastMessageMemmber.getDisplayName());
				conversationJson.put("lastDate", Formatter.formatTimeStampForMessage(lastMessage.getTime()));
				int conversationNumber = messagingService.getConversationNumber(conversation, member);
				conversationJson.put("conversationLink", "#/message/" + conversationNumber);
				conversationJson.put("lastMessageLink", "#/message/" + conversationNumber + "/"
						+ NavigationUtils.getPageOfElement(lastMessage.getMessageNumber()));
				conversationJson.put("lastPosterLink", "#/user/" + lastMessageMemmber.getId());
				conversationJson.put("starterLink", "#/user/" + firstMessageMember.getId());
				conversationJson.put("firstMessageImageLink",
						LinkUtils.getProfilePictureLink(firstMessageMember.getPictureId()));
				conversationJson.put("lastMessageImageLink",
						LinkUtils.getProfilePictureLink(lastMessageMemmber.getPictureId()));
				conversationJson.put("starterStyle", Formatter.getMemberStyle(firstMessageMember));
				conversationJson.put("lastPosterStyle", Formatter.getMemberStyle(lastMessageMemmber));
				
				conversationsJsonArray.put(conversationJson);
			}
			result.put("conversations", conversationsJsonArray);
			result.put("pages", NavigationUtils.getPagesJsonArray("#/conversations", pageNumber,
					messagingService.getConversationsCount(member)));
		} else {
			result.put("error", true);
		}
		return result.toString();
	}

	@POST
	@Path("new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createConversation(@Context Member member, String data) {
		JSONObject json = new JSONObject(data);
		JSONObject result = new JSONObject();
		if (member != null) {
			String subject = json.getString("subject");
			String text = json.getString("text");
			String recipients = json.getString("recipients");

			String[] splits = recipients.split(";");
			if (splits.length == 0) {
				result.put("success", false);
				result.put("message", "No recipients found.");
				return result.toString();
			}
			List<Member> members = new ArrayList<>();
			for (String split : splits) {
				if (split.equals(member.getDisplayName())) {
					result.put("success", false);
					result.put("message", "Cant send message to yourself!");
					return result.toString();
				}
				List<Member> list = memberBean.findEntitiesByEquality(Member_.displayName, split);
				if (list.size() > 0) {
					members.add(list.get(0));
				} else {
					result.put("success", false);
					result.put("message", "Cant find member: " + split + ".");
					return result.toString();
				}
			}

			if (members.size() > 0) {
				members.add(member);
				
				Conversation conversation = new Conversation();
				conversation.setMessageCount(1);
				conversation.setTitle(subject);
				conversationBean.add(conversation);
				for (Member m : members) {
					int conversationsCount = messagingService.getConversationsCount(member);
					conversationsCount++;
					ConversationToMember conversationToMember = new ConversationToMember();
					conversationToMember.setMember(m);
					conversationToMember.setConversation(conversation);
					conversationToMember.setConversationNumber(conversationsCount);
					conversationToMemberBean.add(conversationToMember);
				}

				Message message = new Message();
				message.setConversation(conversation);
				message.setMessageNumber(1);
				message.setTime(Timestamp.valueOf(LocalDateTime.now()));
				message.setText(text);
				message.setMember(member);
				messageBean.add(message);

				ConversationSeenByMember conversationSeenByMember = new ConversationSeenByMember();
				conversationSeenByMember.setConversation(conversation);
				conversationSeenByMember.setMember(member);
				conversationSeenByMember.setSeenMessageNumber(1);
				conversationSeenByMemberBean.add(conversationSeenByMember);
			} 
		}

		result.put("success", true);
		return result.toString();
	}

}
