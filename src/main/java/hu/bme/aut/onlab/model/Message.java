package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "message")
public class Message {
    private int id;
    private int messageNumber;
    private String text;
    private Timestamp time;
    private byte seen;
    private Collection<Member> membersById;
    private Conversation conversationByConversationId;
    private int conversationId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (messageNumber != message.messageNumber) return false;
        if (seen != message.seen) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (time != null ? !time.equals(message.time) : message.time != null) return false;
        if (membersById != null ? !membersById.equals(message.membersById) : message.membersById != null) return false;
        return !(conversationByConversationId != null ? !conversationByConversationId.equals(message.conversationByConversationId) : message.conversationByConversationId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + messageNumber;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (int) seen;
        result = 31 * result + (membersById != null ? membersById.hashCode() : 0);
        result = 31 * result + (conversationByConversationId != null ? conversationByConversationId.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "message_number", nullable = false, insertable = true, updatable = true)
    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    @Basic
    @Column(name = "text", nullable = false, insertable = true, updatable = true, length = 255)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "seen", nullable = false, insertable = true, updatable = true)
    public byte getSeen() {
        return seen;
    }

    public void setSeen(byte seen) {
        this.seen = seen;
    }

    @OneToMany(mappedBy = "messageByMessageid")
    public Collection<Member> getMembersById() {
        return membersById;
    }

    public void setMembersById(Collection<Member> membersById) {
        this.membersById = membersById;
    }

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
    public Conversation getConversationByConversationId() {
        return conversationByConversationId;
    }

    public void setConversationByConversationId(Conversation conversationByConversationId) {
        this.conversationByConversationId = conversationByConversationId;
    }

    @Basic
    @Column(name = "conversation_id", nullable = false, insertable = true, updatable = true)
    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
}
