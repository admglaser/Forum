package hu.bme.aut.onlab.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
public class Topic {
    private int id;
    private int title;
    private int subcategoryId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    @Basic
    @Column(name = "subcategory_id")
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (id != topic.id) return false;
        if (title != topic.title) return false;
        if (subcategoryId != topic.subcategoryId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title;
        result = 31 * result + subcategoryId;
        return result;
    }
}
