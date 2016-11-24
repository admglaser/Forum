package hu.bme.aut.onlab.model;

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
@Table(name = "topic")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")	
	private String title;

	@Column(name = "view_count")
	private int viewCount;

	@OneToMany(mappedBy = "topic")
	private List<Post> posts;

	@ManyToOne
	@JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
	private Subcategory subcategory;

	@OneToMany(mappedBy = "topic")
	private List<TopicSubscription> topicSubscriptions;
	
	@OneToMany(mappedBy = "topic")
	private List<TopicSeenByMember> topicSeenByMembers;
	
	@Column(name = "subcategory_id", insertable = false, updatable = false)
	protected int subcategoryId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public List<TopicSubscription> getTopicSubscriptions() {
		return topicSubscriptions;
	}

	public void setTopicSubscriptions(List<TopicSubscription> topicSubscriptions) {
		this.topicSubscriptions = topicSubscriptions;
	}

	public List<TopicSeenByMember> getTopicSeenByMembers() {
		return topicSeenByMembers;
	}

	public void setTopicSeenByMembers(List<TopicSeenByMember> topicSeenByMembers) {
		this.topicSeenByMembers = topicSeenByMembers;
	}

	private int getSubcategoryId() {
		return subcategoryId;
	}

	private void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	
}
