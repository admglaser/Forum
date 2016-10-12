package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "conversation")
public class Conversation {
    private int id;
    private String title;
    private int messageCount;
    private Collection<Message> messagesById;
    private Set<Member> members;
    private Collection<ConversationSeenByMember> conversationSeenByMembersById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "message_count", nullable = false, insertable = true, updatable = true)
    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;

        if (id != that.id) return false;
        if (messageCount != that.messageCount) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + messageCount;
        return result;
    }

    @ManyToMany
    @JoinTable(name = "conversation_to_member", catalog = "forum", schema = "", joinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false))
    public Set<Member> getMembers() {
        return members;
    }

    public void setMembers(Set<Member> members) {
        this.members = members;
    }

    @OneToMany(mappedBy = "conversationByConversationId")
    public Collection<Message> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(Collection<Message> messagesById) {
        this.messagesById = messagesById;
    }

    @OneToMany(mappedBy = "conversationByConversationId")
    public Collection<ConversationSeenByMember> getConversationSeenByMembersById() {
        return conversationSeenByMembersById;
    }

    public void setConversationSeenByMembersById(Collection<ConversationSeenByMember> conversationSeenByMembersById) {
        this.conversationSeenByMembersById = conversationSeenByMembersById;
    }
}
