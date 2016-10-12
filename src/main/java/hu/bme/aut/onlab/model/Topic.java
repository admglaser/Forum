package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "topic")
public class Topic {
    private int id;
    private String title;
    private int subcategoryId;
    private Collection<Post> postsById;
    private Subcategory subcategoryBySubcategoryId;
    private Collection<TopicSubscription> topicSubscriptionsById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (id != topic.id) return false;
        if (subcategoryId != topic.subcategoryId) return false;
        return !(title != null ? !title.equals(topic.title) : topic.title != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + subcategoryId;
        return result;
    }

    @OneToMany(mappedBy = "topicByTopicId")
    public Collection<Post> getPostsById() {
        return postsById;
    }

    public void setPostsById(Collection<Post> postsById) {
        this.postsById = postsById;
    }

    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
    public Subcategory getSubcategoryBySubcategoryId() {
        return subcategoryBySubcategoryId;
    }

    public void setSubcategoryBySubcategoryId(Subcategory subcategoryBySubcategoryId) {
        this.subcategoryBySubcategoryId = subcategoryBySubcategoryId;
    }

    @OneToMany(mappedBy = "topicByTopicId")
    public Collection<TopicSubscription> getTopicSubscriptionsById() {
        return topicSubscriptionsById;
    }

    public void setTopicSubscriptionsById(Collection<TopicSubscription> topicSubscriptionsById) {
        this.topicSubscriptionsById = topicSubscriptionsById;
    }

    @Basic
    @Column(name = "subcategory_id", nullable = false, insertable = true, updatable = true)
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    private Collection<TopicSeenByMember> topicSeenByMembersById;

    @OneToMany(mappedBy = "topicByTopicId")
    public Collection<TopicSeenByMember> getTopicSeenByMembersById() {
        return topicSeenByMembersById;
    }

    public void setTopicSeenByMembersById(Collection<TopicSeenByMember> topicSeenByMembersById) {
        this.topicSeenByMembersById = topicSeenByMembersById;
    }
}
