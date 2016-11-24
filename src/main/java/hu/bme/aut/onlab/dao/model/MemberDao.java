package hu.bme.aut.onlab.dao.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Member;

@LocalBean
@Stateless
public class MemberDao extends BaseDao<Member> {

	public MemberDao() {
		super(Member.class);
	}

}
