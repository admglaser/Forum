package hu.bme.aut.onlab.dto.out.home;

import hu.bme.aut.onlab.dto.out.BaseOutDto;

import java.util.List;

public class HomeDto extends BaseOutDto {

	private boolean loggedIn;
	private List<CategoryHomeDto> categories;

	public HomeDto() {
		super();
	}

	public boolean getLoggedIn() {
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
