package hu.bme.aut.onlab.bean.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Category;

@LocalBean
@Stateless
public class CategoryBean extends BaseBean<Category> {

	public CategoryBean() {
		super(Category.class);
	}

}
