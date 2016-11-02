package hu.bme.aut.onlab.beans.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Subcategory;

@LocalBean
@Stateless
public class SubcategoryBean extends BaseBean<Subcategory> {

	public SubcategoryBean() {
		super(Subcategory.class);
	}

}
