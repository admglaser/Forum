package hu.bme.aut.onlab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "conversation_to_member")
public class ConversationToMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
	private Conversation conversation;
	
	@Column(name = "conversation_number")
	private int conversationNumber;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public int getConversationNumber() {
		return conversationNumber;
	}

	public void setConversationNumber(int conversationNumber) {
		this.conversationNumber = conversationNumber;
	}
	
}
