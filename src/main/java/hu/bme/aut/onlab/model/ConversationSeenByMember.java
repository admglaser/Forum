package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "conversation_seen_by_member", schema = "", catalog = "forum")
public class ConversationSeenByMember {
    private int id;
    private int seenMessageNumber;
    private int memberId;
    private int conversationId;
    private Member memberByMemberId;
    private Conversation conversationByConversationId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "seen_message_number", nullable = false, insertable = true, updatable = true)
    public int getSeenMessageNumber() {
        return seenMessageNumber;
    }

    public void setSeenMessageNumber(int seenMessageNumber) {
        this.seenMessageNumber = seenMessageNumber;
    }

    @Basic
    @Column(name = "member_id", nullable = false, insertable = true, updatable = true)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "conversation_id", nullable = false, insertable = true, updatable = true)
    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationSeenByMember that = (ConversationSeenByMember) o;

        if (id != that.id) return false;
        if (seenMessageNumber != that.seenMessageNumber) return false;
        if (memberId != that.memberId) return false;
        if (conversationId != that.conversationId) return false;
        if (memberByMemberId != null ? !memberByMemberId.equals(that.memberByMemberId) : that.memberByMemberId != null)
            return false;
        return !(conversationByConversationId != null ? !conversationByConversationId.equals(that.conversationByConversationId) : that.conversationByConversationId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + seenMessageNumber;
        result = 31 * result + memberId;
        result = 31 * result + conversationId;
        result = 31 * result + (memberByMemberId != null ? memberByMemberId.hashCode() : 0);
        result = 31 * result + (conversationByConversationId != null ? conversationByConversationId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
    public Conversation getConversationByConversationId() {
        return conversationByConversationId;
    }

    public void setConversationByConversationId(Conversation conversationByConversationId) {
        this.conversationByConversationId = conversationByConversationId;
    }
}
