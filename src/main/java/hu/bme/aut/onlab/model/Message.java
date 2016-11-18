package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Timestamp;

@SuppressWarnings("unused")
@Entity
@Table(name = "message")
public class Message {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "message_number")    
	private int messageNumber;
    
	@Column(name = "text")
	private String text;
    
	@Column(name = "time")
	private Timestamp time;
    
    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
    private Conversation conversation;
    
    @Column(name = "conversation_id", insertable = false, updatable = false)
    protected int conversationId;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @Column(name = "member_id", insertable = false, updatable = false)
    protected int memberId;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

	private int getConversationId() {
		return conversationId;
	}

	private void setConversationId(int conversationId) {
		this.conversationId = conversationId;
	}

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    private int getMemberId() {
        return memberId;
    }

    private void setMemberId(int memberId) {
        this.memberId = memberId;
    }

}
