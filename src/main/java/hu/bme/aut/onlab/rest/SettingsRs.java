package hu.bme.aut.onlab.rest;

import java.io.InputStream;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import hu.bme.aut.onlab.bean.UploadService;
import hu.bme.aut.onlab.bean.dao.MemberBean;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.util.LinkUtils;

@Path("/settings")
public class SettingsRs {
	
	@EJB
	private UploadService uploadService;
	
	@EJB
	private MemberBean memberBean;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSettings(@Context Member member) {
		JSONObject result = new JSONObject();
		if (member != null) {
			result.put("imageLink", LinkUtils.getProfilePictureLink(member.getPictureId()));
		}
        return result.toString();
    }
	
	@POST
	@Path("/photo")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadPhoto(@Context Member member, InputStream inputStream) {
		JSONObject result = new JSONObject();
		try {
			String pictureId = uploadService.addImage(inputStream);
			member.setPictureId(pictureId);
			memberBean.merge(member);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("errorMessage", e.getMessage());
		}
		return result.toString();
	}
	
}
