package hu.bme.aut.onlab.rest;

import hu.bme.aut.onlab.dto.in.topic.CreatePostRequestDto;
import hu.bme.aut.onlab.dto.in.topic.FollowTopicRequestDto;
import hu.bme.aut.onlab.dto.in.topic.LikePostRequestDto;
import hu.bme.aut.onlab.dto.out.PostResponseDto;
import hu.bme.aut.onlab.dto.out.topic.TopicDto;
import hu.bme.aut.onlab.model.Member;
import hu.bme.aut.onlab.service.TopicService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("topic")
public class TopicRs {

	@EJB
	private TopicService topicService;

	@GET
	@Path("{topicId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TopicDto getTopic(@Context Member member, @PathParam("topicId") int topicId) {
		return topicService.getTopic(member, topicId, 1);
	}

	@GET
	@Path("{topicId}/{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public TopicDto getTopicWithPage(@Context Member member, @PathParam("topicId") int topicId, @PathParam("pageNumber") int pageNumber) {
		return topicService.getTopic(member, topicId, pageNumber);
	}


	@POST
	@Path("new")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostResponseDto createPost(@Context Member member, CreatePostRequestDto requestDto) {
		return topicService.createPost(member, requestDto);
	}

	@POST
	@Path("follow")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostResponseDto followTopic(@Context Member member, FollowTopicRequestDto requestDto) {
		return topicService.followTopic(member, requestDto);
	}

	@POST
	@Path("like")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostResponseDto likePost(@Context Member member, LikePostRequestDto requestDto) {
		return topicService.likePost(member, requestDto);
	}
}
