package hu.bme.aut.onlab.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "conversation")
public class Conversation {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
//	@Column(name = "conversation_number")
//	private int conversationNumber;
	
    @OneToMany(mappedBy = "conversation")
    private List<ConversationToMember> conversationToMemberList;
	
	@Column(name = "title")
	private String title;
    
	@Column(name = "message_count")
	private int messageCount;
    
	@OneToMany(mappedBy = "conversation")
	private List<Message> messages;
    
	@ManyToMany
	@JoinTable(name = "conversation_to_member",
		joinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false), 
		inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false))
	private List<Member> members;
    
	@OneToMany(mappedBy = "conversation")
	private List<ConversationSeenByMember> conversationSeenByMembers;
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getConversationNumber() {
//        return conversationNumber;
//    }

//    public void setConversationNumber(int conversationNumber) {
//        this.conversationNumber = conversationNumber;
//    }
    
    public List<ConversationToMember> getConversationToMemberList() {
    	return conversationToMemberList;
    }

    public void setConversationToMemberList(List<ConversationToMember> conversationToMemberList) {
    	this.conversationToMemberList = conversationToMemberList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<ConversationSeenByMember> getConversationSeenByMembers() {
        return conversationSeenByMembers;
    }

    public void setConversationSeenByMembers(List<ConversationSeenByMember> conversationSeenByMembers) {
        this.conversationSeenByMembers = conversationSeenByMembers;
    }
    
}
