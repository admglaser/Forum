package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
@Table(name = "topic_subscription", schema = "", catalog = "forum")
public class TopicSubscription {
    private int id;
    private int topicId;
    private int memberId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "topic_id")
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Basic
    @Column(name = "member_id")
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TopicSubscription that = (TopicSubscription) o;

        if (id != that.id) return false;
        if (topicId != that.topicId) return false;
        if (memberId != that.memberId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + topicId;
        result = 31 * result + memberId;
        return result;
    }
}
