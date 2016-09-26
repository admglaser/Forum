package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
@Table(name = "like")
public class Like {
    private int id;
    private int memberId;
    private int postId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "member_id")
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "post_id")
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Like like = (Like) o;

        if (id != like.id) return false;
        if (memberId != like.memberId) return false;
        if (postId != like.postId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + memberId;
        result = 31 * result + postId;
        return result;
    }
}
