package hu.bme.aut.onlab.dto.out.home;

import java.util.List;

public class HomeDto {

	private boolean loggedIn;
	private List<CategoryHomeDto> categories;

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public List<CategoryHomeDto> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryHomeDto> categories) {
		this.categories = categories;
	}

}
