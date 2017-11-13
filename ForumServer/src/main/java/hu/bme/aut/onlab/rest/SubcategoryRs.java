package hu.bme.aut.onlab.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import hu.bme.aut.onlab.dao.LoginDao;
import hu.bme.aut.onlab.dao.model.CategoryDao;
import hu.bme.aut.onlab.dto.in.subcategory.CreateTopicRequestDto;
import hu.bme.aut.onlab.dto.in.subcategory.FollowSubcategoryRequestDto;
import hu.bme.aut.onlab.dto.out.PostResponseDto;
import hu.bme.aut.onlab.dto.out.subcategory.CreateTopicResponseDto;
import hu.bme.aut.onlab.dto.out.subcategory.SubcategoryDto;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.service.SubcategoryService;

@Path("/category")
public class SubcategoryRs {

    @EJB
    private SubcategoryService subcategoryService;
    
    @EJB
    private LoginDao loginService;
    
    @EJB
    private CategoryDao categoryBean;

    @GET
    @Path("{subcategoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public SubcategoryDto getSubcategory(@Context Member member, @PathParam("subcategoryId") int subcategoryId) {
    	return subcategoryService.getSubcategory(member, subcategoryId, 1);
    }
    
    @GET
    @Path("{subcategoryId}/{pageNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public SubcategoryDto getSubcategoryWithPage(@Context Member member, @PathParam("subcategoryId") int subcategoryId, @PathParam("pageNumber") int pageNumber) {
        return subcategoryService.getSubcategory(member, subcategoryId, pageNumber);
    }

    @POST
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateTopicResponseDto createTopic(@Context Member member, CreateTopicRequestDto requestDto) {
        return subcategoryService.createTopic(member, requestDto);
    }

    @POST
    @Path("follow")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PostResponseDto followSubcategory(@Context Member member, FollowSubcategoryRequestDto requestDto) {
        return subcategoryService.followSubcategory(member, requestDto);
    }
}
