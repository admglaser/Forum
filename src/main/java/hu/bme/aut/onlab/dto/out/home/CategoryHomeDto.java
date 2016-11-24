package hu.bme.aut.onlab.dto.out.home;

import java.util.List;

public class CategoryHomeDto {

	private String title;
	private List<SubcategoryHomeDto> subcategories;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<SubcategoryHomeDto> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<SubcategoryHomeDto> subcategories) {
		this.subcategories = subcategories;
	}

}
