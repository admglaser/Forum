package hu.bme.aut.onlab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ExcludeSuperclassListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "subcategory_subscription")
public class SubcategorySubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
	private Member member;

	@ManyToOne
	@JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
	private Subcategory subcategory;
	
	@Column(name = "member_id", insertable = false, updatable = false)
	protected int memberId;
	
	@Column(name = "subcategory_id", insertable = false, updatable = false)
	protected int subcategoryId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getMemberByMemberId() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	private int getMemberId() {
		return memberId;
	}

	private void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	private int getSubcategoryId() {
		return subcategoryId;
	}

	private void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
	}
	
}
