package hu.bme.aut.onlab.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "message")
public class Message {
    
	@Id
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

}
