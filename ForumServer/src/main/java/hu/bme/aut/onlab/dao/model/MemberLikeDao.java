package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.MemberLike;

@LocalBean
@Stateless
public class MemberLikeDao extends BaseDao<MemberLike> {

	public MemberLikeDao() {
		super(MemberLike.class);
	}

}
