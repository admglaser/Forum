package hu.bme.aut.onlab.beans;

import java.util.Map;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import hu.bme.aut.onlab.beans.dao.MemberBean;
import hu.bme.aut.onlab.beans.helper.CriteriaHelper;
import hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.Member_;

@LocalBean
@Stateful
@ApplicationScoped
public class LoginService {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private MemberBean memberBean;

	private Map<String, Integer> membersMap;

	public Member getCurrentMember() {
		
		return memberBean.findAllEntity().get(0);
	}
	
	public Member getMember(String uuid) {
		if (membersMap.containsKey(uuid)) {
			int id = membersMap.get(uuid);
			return memberBean.findEntityById(id);
		} else {
			return null;
		}
	}

	public Member login(String username, String password) {
		CriteriaHelper<Member> criteriaHelper = new CriteriaHelper<>(Member.class, em, CriteriaType.SELECT);
		CriteriaQuery<Member> criteriaQuery = criteriaHelper.getCriteriaQuery();
		Root<Member> rootEntity = criteriaHelper.getRootEntity();
		CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();

		criteriaQuery.where(
				criteriaBuilder.and(
						criteriaBuilder.equal(rootEntity.get(Member_.userName), username),
						criteriaBuilder.equal(rootEntity.get(Member_.password), password)
				)
		);

		criteriaQuery.select(rootEntity);

		try {
			Member member = em.createQuery(criteriaQuery).getSingleResult();
			String uuid = UUID.randomUUID().toString();
			membersMap.put(uuid, member.getId());
			return member;
		} catch (NoResultException e) {
			return null;
		}
	}

}
