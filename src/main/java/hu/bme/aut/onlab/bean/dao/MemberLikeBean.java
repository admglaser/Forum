package hu.bme.aut.onlab.bean.dao;

import hu.bme.aut.onlab.model.MemberLike;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class MemberLikeBean extends BaseBean<MemberLike> {

	public MemberLikeBean() {
		super(MemberLike.class);
	}

}
