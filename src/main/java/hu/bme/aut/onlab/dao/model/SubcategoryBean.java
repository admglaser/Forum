package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Subcategory;

@LocalBean
@Stateless
public class SubcategoryBean extends BaseDao<Subcategory> {

	public SubcategoryBean() {
		super(Subcategory.class);
	}

}
