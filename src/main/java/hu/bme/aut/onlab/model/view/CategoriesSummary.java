package hu.bme.aut.onlab.model.view;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "v_categories_summary", schema = "", catalog = "forum")
public class CategoriesSummary {
    private int subcategoryId;
    private String subcategoryTitle;
    private String subcategoryDescription;
    private int categoryId;
    private String categoryTitle;
    private long topicCount;
    private long postCount;
    private String lastPostTopicTitle;
    private String lastPostMemberName;
    private Timestamp lastPostPostTime;

    @Basic
    @Column(name = "subcategory_id", nullable = false, insertable = false, updatable = false)
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Basic
    @Column(name = "subcategory_title", nullable = false, insertable = false, updatable = false, length = 255)
    public String getSubcategoryTitle() {
        return subcategoryTitle;
    }

    public void setSubcategoryTitle(String subcategoryTitle) {
        this.subcategoryTitle = subcategoryTitle;
    }

    @Basic
    @Column(name = "subcategory_description", nullable = true, insertable = false, updatable = false, length = 255)
    public String getSubcategoryDescription() {
        return subcategoryDescription;
    }

    public void setSubcategoryDescription(String subcategoryDescription) {
        this.subcategoryDescription = subcategoryDescription;
    }

    @Id
    @Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "category_title", nullable = false, insertable = false, updatable = false, length = 255)
    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    @Basic
    @Column(name = "topic_count", nullable = false, insertable = false, updatable = false)
    public long getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(long topicNumber) {
        this.topicCount = topicNumber;
    }

    @Basic
    @Column(name = "post_count", nullable = false, insertable = false, updatable = false)
    public long getPostCount() {
        return postCount;
    }

    public void setPostCount(long postNumber) {
        this.postCount = postNumber;
    }

    @Basic
    @Column(name = "last_post_topic_title", nullable = true, insertable = false, updatable = false, length = 255)
    public String getLastPostTopicTitle() {
        return lastPostTopicTitle;
    }

    public void setLastPostTopicTitle(String lastPostTopicTitle) {
        this.lastPostTopicTitle = lastPostTopicTitle;
    }

    @Basic
    @Column(name = "last_post_member_name", nullable = true, insertable = false, updatable = false, length = 255)
    public String getLastPostMemberName() {
        return lastPostMemberName;
    }

    public void setLastPostMemberName(String lastPostMemberName) {
        this.lastPostMemberName = lastPostMemberName;
    }

    @Basic
    @Column(name = "last_post_post_time", nullable = true, insertable = false, updatable = false)
    public Timestamp getLastPostPostTime() {
        return lastPostPostTime;
    }

    public void setLastPostPostTime(Timestamp lastPostPostTime) {
        this.lastPostPostTime = lastPostPostTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriesSummary that = (CategoriesSummary) o;

        if (subcategoryId != that.subcategoryId) return false;
        if (categoryId != that.categoryId) return false;
        if (topicCount != that.topicCount) return false;
        if (postCount != that.postCount) return false;
        if (subcategoryTitle != null ? !subcategoryTitle.equals(that.subcategoryTitle) : that.subcategoryTitle != null)
            return false;
        if (subcategoryDescription != null ? !subcategoryDescription.equals(that.subcategoryDescription) : that.subcategoryDescription != null)
            return false;
        if (categoryTitle != null ? !categoryTitle.equals(that.categoryTitle) : that.categoryTitle != null)
            return false;
        if (lastPostTopicTitle != null ? !lastPostTopicTitle.equals(that.lastPostTopicTitle) : that.lastPostTopicTitle != null)
            return false;
        if (lastPostMemberName != null ? !lastPostMemberName.equals(that.lastPostMemberName) : that.lastPostMemberName != null)
            return false;
        if (lastPostPostTime != null ? !lastPostPostTime.equals(that.lastPostPostTime) : that.lastPostPostTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subcategoryId;
        result = 31 * result + (subcategoryTitle != null ? subcategoryTitle.hashCode() : 0);
        result = 31 * result + (subcategoryDescription != null ? subcategoryDescription.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + (categoryTitle != null ? categoryTitle.hashCode() : 0);
        result = 31 * result + (int) (topicCount ^ (topicCount >>> 32));
        result = 31 * result + (int) (postCount ^ (postCount >>> 32));
        result = 31 * result + (lastPostTopicTitle != null ? lastPostTopicTitle.hashCode() : 0);
        result = 31 * result + (lastPostMemberName != null ? lastPostMemberName.hashCode() : 0);
        result = 31 * result + (lastPostPostTime != null ? lastPostPostTime.hashCode() : 0);
        return result;
    }
}
