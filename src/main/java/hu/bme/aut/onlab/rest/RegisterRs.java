package hu.bme.aut.onlab.rest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import hu.bme.aut.onlab.dao.RegistrationDao;
import hu.bme.aut.onlab.dao.model.MemberDao;
import hu.bme.aut.onlab.model.Member;

@Path("register")
public class RegisterRs {

    @EJB
    private RegistrationDao registrationService;

    @EJB
    private MemberDao memberBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(String data) {
        JSONObject input = new JSONObject(data);
        JSONObject result = new JSONObject();
        String message;
        	
        String username = input.getString("username");
        String displayName = input.getString("displayName");
        String email = input.getString("email");
        String password = input.getString("password");
        String confirmPassword = input.getString("confirmPassword");
        String birthDateString = input.getString("birthDate");

        // Validate all field
        if ((message = registrationService.validateUsername(username)) == null) {
            if ((message = registrationService.validateDisplayName(displayName)) == null) {
                if ((message = registrationService.validateEmail(email)) == null) {
                    if ((message = registrationService.validatePassword(password)) == null) {
                        if ((message = registrationService.validateConfirmPassword(confirmPassword, password)) == null) {
                            Member member = new Member();
                            member.setUserName(username);
                            member.setDisplayName(displayName);
                            member.setEmail(email);
                            member.setPassword(password);
                            member.setPictureId("default");
                            member.setRegisterTime(Timestamp.valueOf(LocalDateTime.now()));
                            member.setMemberGroup(registrationService.getMemberGroupByTitle(RegistrationDao.DEFAULT_MEMBER_GROUP));
                            member.setViewsCount(0);
                            member.setPostCount(0);
                            member.setTopicCount(0);
                            // Validation of birth date is not necessary because of the date picker.
                            if ((birthDateString != null) && (! birthDateString.isEmpty())) {
                                member.setBirthday(Date.valueOf(birthDateString));
                            }

                            memberBean.add(member);

                            result.put("success", true);
                            result.put("message", "Registration successful.");
                            return result.toString();
                        }
                    }
                }
            }
        }

        result.put("success", false);
        result.put("message", message);
        return result.toString();
    }
}
