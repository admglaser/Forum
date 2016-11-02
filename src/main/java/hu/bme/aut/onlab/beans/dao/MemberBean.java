package hu.bme.aut.onlab.beans.dao;

import hu.bme.aut.onlab.model.Member;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class MemberBean extends BaseBean<Member> {

	public MemberBean() {
		super(Member.class);
	}

}
