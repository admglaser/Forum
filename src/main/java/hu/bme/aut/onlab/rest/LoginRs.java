package hu.bme.aut.onlab.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import hu.bme.aut.onlab.model.Member;

@Path("/login")
public class LoginRs {

	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getHome(@Context Member member) {
		  JSONObject result = new JSONObject();
		  result.put("success", member != null);
		  return result.toString();
	  }
	  
}
