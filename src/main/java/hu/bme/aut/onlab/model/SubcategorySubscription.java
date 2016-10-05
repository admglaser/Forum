package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "subcategory_subscription")
public class SubcategorySubscription {
    private int id;
    private Member memberByMemberId;
    private Subcategory subcategoryBySubcategoryId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubcategorySubscription that = (SubcategorySubscription) o;

        if (id != that.id) return false;
        if (memberByMemberId != null ? !memberByMemberId.equals(that.memberByMemberId) : that.memberByMemberId != null)
            return false;
        return !(subcategoryBySubcategoryId != null ? !subcategoryBySubcategoryId.equals(that.subcategoryBySubcategoryId) : that.subcategoryBySubcategoryId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (memberByMemberId != null ? memberByMemberId.hashCode() : 0);
        result = 31 * result + (subcategoryBySubcategoryId != null ? subcategoryBySubcategoryId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }

    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
    public Subcategory getSubcategoryBySubcategoryId() {
        return subcategoryBySubcategoryId;
    }

    public void setSubcategoryBySubcategoryId(Subcategory subcategoryBySubcategoryId) {
        this.subcategoryBySubcategoryId = subcategoryBySubcategoryId;
    }
}
