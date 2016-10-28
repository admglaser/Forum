package hu.bme.aut.onlab.beans.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import hu.bme.aut.onlab.model.Member;


@LocalBean
@Stateless
public class MemberBean extends BaseBean<Member> {

	public MemberBean() {
		super(Member.class);
	}

}
