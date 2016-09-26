package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
public class Conversation {
    private int id;
    private String title;
    private int messageCount;
    private Set<Member> members;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "message_count")
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
}
