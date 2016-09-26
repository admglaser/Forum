package hu.bme.aut.onlab.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
public class Subcategory {
    private int id;
    private int title;
    private Integer desc;
    private int categoryId;

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
    @Column(name = "desc")
    public Integer getDesc() {
        return desc;
    }

    public void setDesc(Integer desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "category_id")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subcategory that = (Subcategory) o;

        if (id != that.id) return false;
        if (title != that.title) return false;
        if (categoryId != that.categoryId) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + categoryId;
        return result;
    }
}
