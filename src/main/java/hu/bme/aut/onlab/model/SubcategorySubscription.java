package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "subcategory_subscription", schema = "", catalog = "forum")
public class SubcategorySubscription {
    private int id;
    private int subcategoryId;
    private int memberId;
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
        if (subcategoryId != that.subcategoryId) return false;
        return memberId == that.memberId;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subcategoryId;
        result = 31 * result + memberId;
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

    @Basic
    @Column(name = "subcategory_id", nullable = false, insertable = false, updatable = false)
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Basic
    @Column(name = "member_id", nullable = false, insertable = false, updatable = false)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
