package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "subcategory")
public class Subcategory {
    private int id;
    private String title;
    private String desc;
    private Collection<Permission> permissionsById;
    private Category categoryByCategoryId;
    private Collection<SubcategorySubscription> subcategorySubscriptionsById;
    private Collection<Topic> topicsById;

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

    @Basic
    @Column(name = "desc", nullable = true, insertable = true, updatable = true, length = 255)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subcategory that = (Subcategory) o;

        if (id != that.id) return false;
        if (categoryId != that.categoryId) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return !(desc != null ? !desc.equals(that.desc) : that.desc != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + categoryId;
        return result;
    }

    @OneToMany(mappedBy = "subcategoryBySubcategoryId")
    public Collection<Permission> getPermissionsById() {
        return permissionsById;
    }

    public void setPermissionsById(Collection<Permission> permissionsById) {
        this.permissionsById = permissionsById;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }

    @OneToMany(mappedBy = "subcategoryBySubcategoryId")
    public Collection<SubcategorySubscription> getSubcategorySubscriptionsById() {
        return subcategorySubscriptionsById;
    }

    public void setSubcategorySubscriptionsById(Collection<SubcategorySubscription> subcategorySubscriptionsById) {
        this.subcategorySubscriptionsById = subcategorySubscriptionsById;
    }

    @OneToMany(mappedBy = "subcategoryBySubcategoryId")
    public Collection<Topic> getTopicsById() {
        return topicsById;
    }

    public void setTopicsById(Collection<Topic> topicsById) {
        this.topicsById = topicsById;
    }

    private int categoryId;

    @Basic
    @Column(name = "category_id", nullable = false, insertable = true, updatable = true)
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
