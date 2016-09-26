package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
@Table(name = "subcategory_subscription", schema = "", catalog = "forum")
public class SubcategorySubscription {
    private int id;
    private int subcategoryId;
    private int memberId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "subcategory_id")
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Basic
    @Column(name = "member_id")
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubcategorySubscription that = (SubcategorySubscription) o;

        if (id != that.id) return false;
        if (subcategoryId != that.subcategoryId) return false;
        if (memberId != that.memberId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subcategoryId;
        result = 31 * result + memberId;
        return result;
    }
}
