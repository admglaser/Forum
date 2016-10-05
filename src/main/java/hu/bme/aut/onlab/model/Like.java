package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "like")
public class Like {
    private int id;
    private Post postByPostId;
    private Member memberByMemberId;

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

        Like like = (Like) o;

        if (id != like.id) return false;
        if (postByPostId != null ? !postByPostId.equals(like.postByPostId) : like.postByPostId != null) return false;
        return !(memberByMemberId != null ? !memberByMemberId.equals(like.memberByMemberId) : like.memberByMemberId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (postByPostId != null ? postByPostId.hashCode() : 0);
        result = 31 * result + (memberByMemberId != null ? memberByMemberId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    public Post getPostByPostId() {
        return postByPostId;
    }

    public void setPostByPostId(Post postByPostId) {
        this.postByPostId = postByPostId;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }
}
