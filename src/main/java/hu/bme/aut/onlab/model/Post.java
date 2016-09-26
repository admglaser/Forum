package hu.bme.aut.onlab.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
public class Post {
    private int id;
    private Integer postId;
    private String text;
    private Timestamp time;
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
    @Column(name = "postId")
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

        Post post = (Post) o;

        if (id != post.id) return false;
        if (topicId != post.topicId) return false;
        if (memberId != post.memberId) return false;
        if (postId != null ? !postId.equals(post.postId) : post.postId != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        if (time != null ? !time.equals(post.time) : post.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + topicId;
        result = 31 * result + memberId;
        return result;
    }
}
