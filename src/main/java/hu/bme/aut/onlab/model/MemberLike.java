package hu.bme.aut.onlab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "member_like")
public class MemberLike {

	@Id
	private int id;
    
	@ManyToOne
	@JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
	private Post post;
    
	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
    
	@Column(name = "post_id", insertable = false, updatable = false)
	protected int postId;
	
	@Column(name = "member_id", insertable = false, updatable = false)
	protected int memberId;
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

	private int getPostId() {
		return postId;
	}

	private void setPostId(int postId) {
		this.postId = postId;
	}

	private int getMemberId() {
		return memberId;
	}

	private void setMemberId(int memberId) {
		this.memberId = memberId;
	}

}
