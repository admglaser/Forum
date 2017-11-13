package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Category;

@LocalBean
@Stateless
public class CategoryDao extends BaseDao<Category> {

	public CategoryDao() {
		super(Category.class);
	}

}
