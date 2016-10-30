package hu.bme.aut.onlab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("unused")
@Entity
public class ConversationSeenByMember {
    
	@Id
	private int id;
    
	@Column(name = "seen_message_number")
    private int seenMessageNumber;
    
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
    private Conversation conversation;
    
    @Column(name = "conversation_id", insertable = false, updatable = false)
	protected int conversationId;
    
    @Column(name = "member_id", insertable = false, updatable = false)
    protected int memberId;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeenMessageNumber() {
        return seenMessageNumber;
    }

    public void setSeenMessageNumber(int seenMessageNumber) {
        this.seenMessageNumber = seenMessageNumber;
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

	private int getConversationId() {
		return conversationId;
	}

	private void setConversationId(int conversationId) {
		this.conversationId = conversationId;
	}

	private int getMemberId() {
		return memberId;
	}

	private void setMemberId(int memberId) {
		this.memberId = memberId;
	}
    
}
