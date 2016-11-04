package hu.bme.aut.onlab.beans;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
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

	private Map<String, Integer> membersMap = new HashMap<>();

	public Member getCurrentMember() {
		
		return memberBean.findAllEntity().get(0);
	}
	
	public Member getMember(String encodedUserPassword) {
		if (membersMap.containsKey(encodedUserPassword)) {
			int id = membersMap.get(encodedUserPassword);
			return memberBean.findEntityById(id);
		} 
		
		CriteriaHelper<Member> criteriaHelper = new CriteriaHelper<>(Member.class, em, CriteriaType.SELECT);
		CriteriaQuery<Member> criteriaQuery = criteriaHelper.getCriteriaQuery();
		Root<Member> rootEntity = criteriaHelper.getRootEntity();
		CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();
		
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword.replaceFirst("Basic" + " ", ""));
			String usernameAndPassword = new String(decodedBytes, "UTF-8");
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			criteriaQuery.where(
					criteriaBuilder.and(
							criteriaBuilder.equal(rootEntity.get(Member_.userName), username),
							criteriaBuilder.equal(rootEntity.get(Member_.password), password)
							)
					);
			criteriaQuery.select(rootEntity);

			Member member = em.createQuery(criteriaQuery).getSingleResult();
			membersMap.put(encodedUserPassword, member.getId());
			return member;
		} catch (Exception e) {
			return null;
		}
	}

}
