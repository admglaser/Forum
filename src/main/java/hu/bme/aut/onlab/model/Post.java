package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "post")
public class Post {
    private int id;
    private Integer postNumber;
    private String text;
    private Timestamp time;
    private Collection<Like> likesById;
    private Topic topicByTopicId;
    private Member memberByMemberId;
    private int topicId;
    private int memberId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "post_number", nullable = false, insertable = true, updatable = true)
    public Integer getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Integer postId) {
        this.postNumber = postId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        if (topicId != post.topicId) return false;
        if (memberId != post.memberId) return false;
        if (postNumber != null ? !postNumber.equals(post.postNumber) : post.postNumber != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        return !(time != null ? !time.equals(post.time) : post.time != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (postNumber != null ? postNumber.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + topicId;
        result = 31 * result + memberId;
        return result;
    }

    @OneToMany(mappedBy = "postByPostId")
    public Collection<Like> getLikesById() {
        return likesById;
    }

    public void setLikesById(Collection<Like> likesById) {
        this.likesById = likesById;
    }

    @ManyToOne
    @JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
    public Topic getTopicByTopicId() {
        return topicByTopicId;
    }

    public void setTopicByTopicId(Topic topicByTopicId) {
        this.topicByTopicId = topicByTopicId;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }

    @Basic
    @Column(name = "topic_id", nullable = false, insertable = false, updatable = false)
    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    @Basic
    @Column(name = "member_id", nullable = false, insertable = false, updatable = false)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
