package hu.bme.aut.onlab.bean;

import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.model.MemberGroup;
import hu.bme.aut.onlab.model.MemberGroup_;
import hu.bme.aut.onlab.model.Member_;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.regex.Pattern;

@LocalBean
@Stateless
public class RegistrationService {

    public static final String DEFAULT_MEMBER_GROUP = "Member";

    @PersistenceContext
    private EntityManager em;

    public String validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return "User name is missing.";
        }

        Pattern pattern = Pattern.compile("[\\w]+");
        if (!pattern.matcher(username).matches()) {
            return "User name may contain the following characters only:\n" +
            		"  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _";
        }

        if (isUsernameExists(username)) {
            return "User name is already in use.";
        }
        return null;
    }

    public String validateDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return "Display name is missing.";
        }

        Pattern pattern = Pattern.compile("[\\w /\\\\.]+");
        if (!pattern.matcher(displayName).matches()) {
            return "Display name may contain the following characters only:\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _, ., \\, /, [space]";
        }

        if (isDisplayNameExists(displayName)) {
            return "Display name is already in use.";
        }
        return null;
    }

    public String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return "E-mail is missing.";
        }

        Pattern pattern = Pattern.compile("[\\w_.%+-]+@([\\w-]+.)+[\\w]{2,6}");
        if (!pattern.matcher(email).matches()) {
            return "E-mail address may contain the following characters only:\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _, @, ., %, +, -";
        }

        if (isEmailExists(email)) {
            return "E-mail is already in use.";
        }
        return null;
    }

    public String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Password is missing.";
        }

        Pattern pattern = Pattern.compile("[\\w.@#$%^&+-=!&&[^\\s]]{5,}");
        if (!pattern.matcher(password).matches()) {
            return "A password must be at least 5 characters long and may contain only the following characters (and must contain at least one):\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _, @, ., #, $, %, ^, &, +, -, =, !";
        }
        return null;
    }

    public String validateConfirmPassword(String confirmPassword, String password) {
        if (! password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }
        return null;
    }

    public boolean isUsernameExists(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Member> root = query.from(Member.class);

        query.select(builder.count(query.from(Member.class)));
        query.where(builder.equal(root.get(Member_.userName), username));

        try {
            return em.createQuery(query).getSingleResult().intValue() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean isDisplayNameExists(String displayName) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Member> root = query.from(Member.class);

        query.select(builder.count(query.from(Member.class)));
        query.where(builder.equal(root.get(Member_.displayName), displayName));

        try {
            return em.createQuery(query).getSingleResult().intValue() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Member> root = query.from(Member.class);

        query.select(builder.count(query.from(Member.class)));
        query.where(builder.equal(root.get(Member_.email), email));

        try {
            return em.createQuery(query).getSingleResult().intValue() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }

    public MemberGroup getMemberGroupByTitle(String title) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<MemberGroup> query = builder.createQuery(MemberGroup.class);
        Root<MemberGroup> root = query.from(MemberGroup.class);

        query.select(root);
        query.where(builder.equal(root.get(MemberGroup_.title), title));

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
