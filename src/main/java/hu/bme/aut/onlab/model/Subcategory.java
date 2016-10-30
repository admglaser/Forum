package hu.bme.aut.onlab.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "subcategory")
public class Subcategory {
	
	@Id
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "desc")
	private String desc;
	
	@OneToMany(mappedBy = "subcategory")
	private List<Permission> permissions;
	
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	private Category category;
	
	@OneToMany(mappedBy = "subcategory")
	private List<SubcategorySubscription> subcategorySubscriptions;
	
	@OneToMany(mappedBy = "subcategory")
	private List<Topic> topics;
	
	@Column(name = "category_id", insertable = false, updatable = false)
	protected int categoryId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<SubcategorySubscription> getSubcategorySubscriptions() {
		return subcategorySubscriptions;
	}

	public void setSubcategorySubscriptions(List<SubcategorySubscription> subcategorySubscriptions) {
		this.subcategorySubscriptions = subcategorySubscriptions;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	private int getCategoryId() {
		return categoryId;
	}

	private void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
