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
            // No username is given
            return "Please give a username.";
        }

        /* A username may contain only the following characters:
             - Alphabetic characters
             - Numeric characters
             - _
          */
        // \A: beginning of input
        // \Z: end of input
        Pattern pattern = Pattern.compile("\\A[\\w]+\\Z");
        if (!pattern.matcher(username).matches()) {
            // Illegal format of username
            return "Illegal format of username.\n" +
                    "A username may contain only the following characters (and must contain at least one):\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _";
        }

        if (isUsernameExists(username)) {
            // Username already in use
            return "Username already in use.\nPlease use another one.";
        }

        return null;
    }

    public String validateDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            // No display name is given
            return "Please give a display name.";
        }

        /* A display name may contain only the following characters:
             - Alphabetic characters
             - Numeric characters
             - _, ., \, /, [space]
          */
        // \A: beginning of input
        // \Z: end of input
        Pattern pattern = Pattern.compile("\\A[\\w /\\\\.]+\\Z");
        if (!pattern.matcher(displayName).matches()) {
            // Illegal format of display name
            return "Illegal format of display name.\n" +
                    "A display name may contain only the following characters (and must contain at least one):\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _, ., \\, /, [space]";
        }

        return null;
    }

    public String validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            // No e-mail address is given
            return "Please give an e-mail address.";
        }

        /* A e-mail address may contain only the following characters:
             - Alphabetic characters
             - Numeric characters
             - _, @, ., %, +, -
          */
        // \A: beginning of input
        // \Z: end of input
        Pattern pattern = Pattern.compile("\\A[\\w_.%+-]+@([\\w-]+.)+[\\w]{2,6}\\Z");
        if (!pattern.matcher(email).matches()) {
            // Illegal format of e-mail address
            return "Illegal format of e-mail address.\n" +
                    "A display name may contain only the following characters (and must contain at least one):\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _, @, ., %, +, -";
        }

        if (isEmailExists(email)) {
            // E-mail address already in use
            return "E-mail address already in use.\nPlease use another one.";
        }

        return null;
    }

    public String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            // No password is given
            return "Please give a password.";
        }

        /* A password must be at least 5 characters long and may contain only the following characters:
             - Alphabetic characters
             - Numeric characters
             - _, @, ., #, $, %, ^, &, +, -, =, !
          */
        // \A: beginning of input
        // \Z: end of input
        Pattern pattern = Pattern.compile("\\A[\\w.@#$%^&+-=!&&[^\\s]]{5,}\\Z");
        if (!pattern.matcher(password).matches()) {
            // Illegal format of password
            return "Illegal format of password.\n" +
                    "A password must be at least 5 characters long and may contain only the following characters (and must contain at least one):\n" +
                    "  - Alphabetic characters\n" +
                    "  - Numeric characters\n" +
                    "  - _, @, ., #, $, %, ^, &, +, -, =, !";
        }

        return null;
    }

    public String validateConfirmPassword(String confirmPassword, String password) {
        if (! password.equals(confirmPassword)) {
            return "The password and the confirmation password are different.\nPlease make them to be the same.";
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
