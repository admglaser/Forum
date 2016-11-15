package hu.bme.aut.onlab.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import hu.bme.aut.onlab.bean.dao.MemberBean;
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

	public Member getMember(String encodedUserPassword) {
		if (membersMap.containsKey(encodedUserPassword)) {
			int id = membersMap.get(encodedUserPassword);
			Member member = memberBean.findEntityById(id);
			updateLastActiveTime(member);
			return member;
		} 
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> query = builder.createQuery(Member.class);
		Root<Member> memberRoot = query.from(Member.class);
		
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(encodedUserPassword.replaceFirst("Basic" + " ", ""));
			String usernameAndPassword = new String(decodedBytes, "UTF-8");
			final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			query.where(
					builder.and(
							builder.equal(memberRoot.get(Member_.userName), username),
							builder.equal(memberRoot.get(Member_.password), password)
							)
					);
			query.select(memberRoot);

			Member member = em.createQuery(query).getSingleResult();
			membersMap.put(encodedUserPassword, member.getId());
			updateLastActiveTime(member);
			return member;
		} catch (Exception e) {
			return null;
		}
	}

	private void updateLastActiveTime(Member member) {
		member.setActiveTime(Timestamp.valueOf(LocalDateTime.now()));
	}

}
