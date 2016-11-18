package hu.bme.aut.onlab.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "post_number")
	private int postNumber;

	@Column(name = "text")
	private String text;

	@Column(name = "time")
	private Timestamp time;

	@OneToMany(mappedBy = "post")
	private List<MemberLike> likes;

	@ManyToOne
	@JoinColumn(name = "topic_id", referencedColumnName = "id", nullable = false)
	private Topic topic;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;
	
	@Column(name = "member_id", insertable = false, updatable = false)
	protected int memberId;
	
	@Column(name = "topic_id", insertable = false, updatable = false)
	protected int topicId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostNumber() {
		return postNumber;
	}

	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public List<MemberLike> getLikes() {
		return likes;
	}

	public void setLikes(List<MemberLike> likes) {
		this.likes = likes;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	private int getMemberId() {
		return memberId;
	}

	private void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	private int getTopicId() {
		return topicId;
	}

	private void setTopicId(int topicId) {
		this.topicId = topicId;
	}

}
