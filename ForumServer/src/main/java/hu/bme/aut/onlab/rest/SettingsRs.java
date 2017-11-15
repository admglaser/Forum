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

import hu.bme.aut.onlab.dao.UploadDao;
import hu.bme.aut.onlab.dao.model.MemberDao;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.util.LinkUtils;

@Path("/settings")
public class SettingsRs {
	
	@EJB
	private UploadDao uploadService;
	
	@EJB
	private MemberDao memberBean;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getSettings(@Context Member member) {
		JSONObject result = new JSONObject();
		if (member != null) {
			result.put("imageLink", LinkUtils.getProfilePictureLink(member.getPictureId()));
		} else {
			result.put("error", true);
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
			result.put("message", "Image uploaded.");
		} catch (Exception e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result.toString();
	}
	
}
