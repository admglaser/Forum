package hu.bme.aut.onlab.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * Created by N. Vilagos.
 */
@Entity
@javax.persistence.Table(name = "topic_seen_by_member", schema = "", catalog = "forum")
public class TopicSeenByMember {
    private int id;

    @Id
    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Timestamp seenTime;

    @Basic
    @javax.persistence.Column(name = "seen_time", nullable = false, insertable = true, updatable = true)
    public Timestamp getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(Timestamp seenTime) {
        this.seenTime = seenTime;
    }

    private int topicId;

    @Basic
    @javax.persistence.Column(name = "topic_id", nullable = false, insertable = true, updatable = true)
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    private int memberId;

    @Basic
    @javax.persistence.Column(name = "member_id", nullable = false, insertable = true, updatable = true)
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

        TopicSeenByMember that = (TopicSeenByMember) o;

        if (id != that.id) return false;
        if (topicId != that.topicId) return false;
        if (memberId != that.memberId) return false;
        if (seenTime != null ? !seenTime.equals(that.seenTime) : that.seenTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (seenTime != null ? seenTime.hashCode() : 0);
        result = 31 * result + topicId;
        result = 31 * result + memberId;
        return result;
    }

    private Member memberByMemberId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }

    private Topic topicByTopicId;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    public Topic getTopicByTopicId() {
        return topicByTopicId;
    }

    public void setTopicByTopicId(Topic topicByTopicId) {
        this.topicByTopicId = topicByTopicId;
    }
}
