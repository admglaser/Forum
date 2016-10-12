package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "topic_subscription", schema = "", catalog = "forum")
public class TopicSubscription {
    private int id;
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
        if (memberByMemberId != null ? !memberByMemberId.equals(that.memberByMemberId) : that.memberByMemberId != null)
            return false;
        return !(topicByTopicId != null ? !topicByTopicId.equals(that.topicByTopicId) : that.topicByTopicId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (memberByMemberId != null ? memberByMemberId.hashCode() : 0);
        result = 31 * result + (topicByTopicId != null ? topicByTopicId.hashCode() : 0);
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

    private int topicId;

    @Basic
    @Column(name = "topic_id", nullable = false, insertable = true, updatable = true)
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    private int memberId;

    @Basic
    @Column(name = "member_id", nullable = false, insertable = true, updatable = true)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
