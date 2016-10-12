package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "topic_subscription", schema = "", catalog = "forum")
public class TopicSubscription {
    private int id;
    private int topicId;
    private int memberId;
    private Member memberByMemberId;
    private Topic topicByTopicId;

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

        TopicSubscription that = (TopicSubscription) o;

        if (id != that.id) return false;
        if (topicId != that.topicId) return false;
        return memberId == that.memberId;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + topicId;
        result = 31 * result + memberId;
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
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    public Topic getTopicByTopicId() {
        return topicByTopicId;
    }

    public void setTopicByTopicId(Topic topicByTopicId) {
        this.topicByTopicId = topicByTopicId;
    }

    @Basic
    @Column(name = "topic_id", nullable = false, insertable = true, updatable = true)
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Basic
    @Column(name = "member_id", nullable = false, insertable = true, updatable = true)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
