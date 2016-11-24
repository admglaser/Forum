package hu.bme.aut.onlab.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hu.bme.aut.onlab.dto.out.home.HomeDto;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.service.HomeService;

@Path("/home")
public class HomeRs  {
   
    @EJB
    private HomeService homeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HomeDto getHome(@Context Member member) {
    	return homeService.getHome(member);
    }
    
}
