package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
@Table(name = "message")
public class Message {
    private int id;
    private int messageNumber;
    private String text;
    private Timestamp time;
    private byte seen;
    private int conversationId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message_number")
    public int getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "seen")
    public byte getSeen() {
        return seen;
    }

    public void setSeen(byte seen) {
        this.seen = seen;
    }

    @Basic
    @Column(name = "conversation_id")
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

        Message message = (Message) o;

        if (id != message.id) return false;
        if (messageNumber != message.messageNumber) return false;
        if (seen != message.seen) return false;
        if (conversationId != message.conversationId) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        if (time != null ? !time.equals(message.time) : message.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + messageNumber;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (int) seen;
        result = 31 * result + conversationId;
        return result;
    }
}
