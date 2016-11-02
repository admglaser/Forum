package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.beans.ForumReadService;
import hu.bme.aut.onlab.beans.LoginService;
import hu.bme.aut.onlab.beans.MessagingService;
import hu.bme.aut.onlab.beans.dao.ConversationBean;
import hu.bme.aut.onlab.beans.dao.MessageBean;
import hu.bme.aut.onlab.model.Conversation;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberGroup;
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
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("message")
public class MessageRs {

    @EJB
    private ForumReadService forumReadService;

    @EJB
    private MessagingService messagingService;

    @EJB
    private LoginService loginService;

    @EJB
    private ConversationBean conversationBean;

    @EJB
    private MessageBean messageBean;

    @GET
    @Path("{conversationNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessages(@PathParam("conversationNumber") int conversationNumber) {
        return getMessagesWithPage(conversationNumber, 1);
    }

    @GET
    @Path("{conversationNumber}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessagesWithPage(@PathParam("conversationNumber") int conversationNumber, @PathParam("pageNumber") int pageNumber) {
        JSONObject result = new JSONObject();
        JSONArray messagesJsonArray = new JSONArray();

        Conversation conversation = conversationBean.findEntityById(conversationNumber);

        if (conversation != null) {
            List<Message> messages = messagingService.getMessagesOfConversationOnPage(conversation, pageNumber);
            // messages is empty if navigated to a too high page number
            if (! messages.isEmpty() ) {
                Message firstMessage = messages.get(0);

                for (Message message : messages) {
                    JSONObject messageJson = new JSONObject();

                    Member member = message.getMember();
                    MemberGroup memberGroup = member.getMemberGroup();

                    messageJson.put("userName", member.getDisplayName());
                    messageJson.put("userLink", "#/user/" + member.getId());
                    messageJson.put("userImageLink", member.getPictureId());
                    messageJson.put("posts", member.getPostCount());
                    messageJson.put("memberGroup", memberGroup.getTitle());
                    messageJson.put("time", Formatter.formatTimeStampForMessage(message.getTime()));
                    messageJson.put("text", message.getText());
                    messageJson.put("messageNumber", message.getMessageNumber());
                    messageJson.put("messageLink", "#/messages/" + conversation.getId() + "/" + pageNumber);
                    messagesJsonArray.put(messageJson);
                }

                result.put("title", conversation.getTitle());
                result.put("starter", (firstMessage != null) ? firstMessage.getMember().getDisplayName() : null);
                result.put("startDate", (firstMessage != null) ? firstMessage.getTime() : null);
                result.put("messages", messagesJsonArray);
                result.put("pages", NavigationUtils.getPagesJsonArray("#/message/" + conversation.getId(), pageNumber, messagingService.getMessagesCountOfConversation(conversation)));
            }
        }
        return result.toString();
    }
}
