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
@Table(name = "conversation_seen_by_member")
public class ConversationSeenByMember {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
	@Column(name = "seen_message_number")
    private int seenMessageNumber;
    
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
    private Conversation conversation;
    
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
    
}
