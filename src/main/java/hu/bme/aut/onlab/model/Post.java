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
    private Integer postId;
    private String text;
    private Timestamp time;
    private Collection<Like> likesById;
    private Topic topicByTopicId;
    private Member memberByMemberId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "postId", nullable = true, insertable = true, updatable = true)
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "text", nullable = true, insertable = true, updatable = true, length = 255)
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
        if (postId != null ? !postId.equals(post.postId) : post.postId != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        if (time != null ? !time.equals(post.time) : post.time != null) return false;
        if (likesById != null ? !likesById.equals(post.likesById) : post.likesById != null) return false;
        if (topicByTopicId != null ? !topicByTopicId.equals(post.topicByTopicId) : post.topicByTopicId != null)
            return false;
        return !(memberByMemberId != null ? !memberByMemberId.equals(post.memberByMemberId) : post.memberByMemberId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (likesById != null ? likesById.hashCode() : 0);
        result = 31 * result + (topicByTopicId != null ? topicByTopicId.hashCode() : 0);
        result = 31 * result + (memberByMemberId != null ? memberByMemberId.hashCode() : 0);
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
