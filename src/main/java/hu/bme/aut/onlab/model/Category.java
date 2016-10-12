package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "category")
public class Category {
    private int id;
    private String title;
    private Collection<Subcategory> subcategoriesById;

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

        Category category = (Category) o;

        if (id != category.id) return false;
        if (title != null ? !title.equals(category.title) : category.title != null) return false;
        return !(subcategoriesById != null ? !subcategoriesById.equals(category.subcategoriesById) : category.subcategoriesById != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (subcategoriesById != null ? subcategoriesById.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "categoryByCategoryId")
    public Collection<Subcategory> getSubcategoriesById() {
        return subcategoriesById;
    }

    public void setSubcategoriesById(Collection<Subcategory> subcategoriesById) {
        this.subcategoriesById = subcategoriesById;
    }
}
