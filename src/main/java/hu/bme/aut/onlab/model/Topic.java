package hu.bme.aut.onlab.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic {

	@Id
	@Column(name = "id")
	private int id;

	private String title;

	@OneToMany(mappedBy = "topic")
	private List<Post> posts;

	@ManyToOne
	@JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
	private Subcategory subcategory;

	@OneToMany(mappedBy = "topic")
	private List<TopicSubscription> topicSubscriptions;
	
	@OneToMany(mappedBy = "topic")
	private List<TopicSeenByMember> topicSeenByMembers;

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
	
}
