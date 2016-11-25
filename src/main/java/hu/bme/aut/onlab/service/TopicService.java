package hu.bme.aut.onlab.service;

import hu.bme.aut.onlab.dao.ForumDao;
import hu.bme.aut.onlab.dao.NotificationsDao;
import hu.bme.aut.onlab.dao.model.*;
import hu.bme.aut.onlab.dto.in.topic.CreatePostRequestDto;
import hu.bme.aut.onlab.dto.in.topic.FollowTopicRequestDto;
import hu.bme.aut.onlab.dto.in.topic.LikePostRequestDto;
import hu.bme.aut.onlab.dto.out.PostResponseDto;
import hu.bme.aut.onlab.dto.out.topic.PostTopicDto;
import hu.bme.aut.onlab.dto.out.topic.TopicDto;
import hu.bme.aut.onlab.model.*;
import hu.bme.aut.onlab.util.Formatter;
import hu.bme.aut.onlab.util.LinkUtils;
import hu.bme.aut.onlab.util.NavigationUtils;
import hu.bme.aut.onlab.util.NotificationUtils;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@LocalBean
@Stateless
public class TopicService {

    @EJB
    private ForumDao forumDao;

    @EJB
    private TopicBean topicBean;

    @EJB
    private PostBean postBean;

    @EJB
    private MemberDao memberBean;

    @EJB
    private NotificationsDao notificationService;

    @EJB
    private TopicSubscriptionBean topicSubscriptionBean;

    @EJB
    private MemberLikeDao memberLikeBean;


    public TopicDto getTopic(Member member, int topicId, int pageNumber) {
        TopicDto topicDto = new TopicDto();

        Topic topic = topicBean.findEntityById(topicId);

        if (topic != null && forumDao.canMemberViewSubcategory(member, topic.getSubcategory())) {
            List<Post> posts = forumDao.getPostsOfTopicOnPage(topic, pageNumber);
            // posts is empty if navigated to a too high page number
            if (! posts.isEmpty() ) {
                Post firstPost = posts.get(0);
                for (Post post : posts) {
                    PostTopicDto postTopicDto = new PostTopicDto();

                    Member memberOfPost = post.getMember();
                    MemberGroup memberGroup = memberOfPost.getMemberGroup();

                    List<Member> members = forumDao.getMembersWhoLikedPost(post);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < members.size(); i++) {
                        sb.append(members.get(i).getDisplayName());
                        if (i < members.size() - 1) {
                            sb.append(", ");
                        }
                    }

                    List<StyleOfMemberGroup> styles = memberGroup.getStyleOfMemberGroups();
                    for (int i = 0; i < styles.size(); i++) {
                        postTopicDto.getStyles().add(styles.get(i).getStyle());
                    }

                    postTopicDto.setLikers(sb.toString());
                    postTopicDto.setUsername(memberOfPost.getDisplayName());
                    postTopicDto.setUserLink("#/user/" + memberOfPost.getId());
                    postTopicDto.setUserImageLink(LinkUtils.getProfilePictureLink(memberOfPost.getPictureId()));
                    postTopicDto.setPostCount(memberOfPost.getPostCount());
                    postTopicDto.setMemberGroup(memberGroup.getTitle());
                    postTopicDto.setTime(Formatter.formatTimeStamp(post.getTime()));
                    postTopicDto.setText(post.getText());
                    postTopicDto.setLikeCount(forumDao.getPostLikesCount(post));
                    postTopicDto.setPostNumber(post.getPostNumber());
                    postTopicDto.setPostLink("#/topic/" + topic.getId() + "/" + pageNumber);
                    postTopicDto.setIsPostLiked(forumDao.getMemberLike(member, post) != null);
                    topicDto.getPosts().add(postTopicDto);
                }

                topicDto.setTitle(topic.getTitle());
                topicDto.setStartedByText(firstPost.getMember().getDisplayName() + ", " + Formatter.formatTimeStamp(firstPost.getTime()));
                topicDto.setIsFollowedByMember(forumDao.isMemberFollowingTopic(member, topic));
                topicDto.setCanFollow((member != null));
                topicDto.setCanReply(forumDao.canMemberReplyInTopic(member, topic));
                topicDto.setPages(NavigationUtils.getListOfPagesDto("#/topic/" + topic.getId(), pageNumber, forumDao.getPostsCountOfTopic(topic)));
            }
            // Renew seen of topic of a member
            forumDao.renewTopicSeenByMember(member, topic);

            // Increase view count of the topic
            topic.setViewCount(topic.getViewCount() + 1);
            topicBean.merge(topic);
        } else {
            topicDto.setError(true);
        }
        return topicDto;
    }

    public PostResponseDto createPost(Member member, CreatePostRequestDto requestDto) {
        PostResponseDto postResponseDto = new PostResponseDto();
        String message;

        if (member != null) {
            int topicId = requestDto.getTopic();
            Topic topic = topicBean.findEntityById(topicId);
            if (topic != null) {
                if (forumDao.canMemberReplyInTopic(member, topic)) {
                    String quotedPostText = requestDto.getQuote();
                    String postText = requestDto.getText();
                    int quotePostNumber = requestDto.getQuotePostNumber();

                    Post lastPostInTopic = forumDao.getLastPostFromTopic(topic);

                    Post post = new Post();
                    post.setTopic(topic);
                    post.setMember(member);
                    post.setText((quotedPostText == null) ? postText : quotedPostText + postText);
                    post.setPostNumber(lastPostInTopic.getPostNumber() + 1);
                    post.setTime(Timestamp.valueOf(LocalDateTime.now()));

                    postBean.add(post);

                    member.setPostCount(member.getPostCount() + 1);
                    memberBean.merge(member);

                    //add mention notification
                    if (postText.contains("@")) {
                        String mentionedName = NotificationUtils.getMentionedName(postText);
                        if (mentionedName != null) {
                            List<Member> list = memberBean.findEntitiesByEquality(Member_.displayName, mentionedName);
                            if (list.size() > 0) {
                                Member mentionedMember = list.get(0);
                                notificationService.addMention(member, mentionedMember, post);
                            }
                        }
                    }

                    //add quote notification
                    if (quotePostNumber > 0) {
                        notificationService.addQuote(member, topicId, quotePostNumber);
                    }

                    //add subscription notification
                    notificationService.addNewReply(post);


                    forumDao.renewTopicSeenByMember(member, topic);

                    postResponseDto.setMessage("Reply created.");
                    postResponseDto.setSuccess(true);
                    return postResponseDto;
                } else {
                    message = "You have no permission to create new reply.";
                }
            } else {
                message = "Unknown topic.";
            }
        } else {
            message = "Unidentified member.";
        }

        postResponseDto.setMessage(message);
        postResponseDto.setSuccess(false);
        return postResponseDto;
    }

    public PostResponseDto followTopic(Member member, FollowTopicRequestDto requestDto) {
        PostResponseDto postResponseDto = new PostResponseDto();
        String message;
        int topicId = requestDto.getTopic();;

        if (member != null) {
            Topic topic = topicBean.findEntityById(topicId);
            if (topic != null) {
                boolean isFollowRequest = requestDto.isFollowRequest();
                TopicSubscription existingTopicSubscription = forumDao.getTopicSubscription(member, topic);

                if (isFollowRequest) {
                    message = "Topic followed.";
                    if (existingTopicSubscription == null) {
                        TopicSubscription topicSubscription = new TopicSubscription();
                        topicSubscription.setMember(member);
                        topicSubscription.setTopic(topic);
                        topicSubscriptionBean.add(topicSubscription);
                    }

                } else {
                    message = "Topic unfollowed.";
                    if (existingTopicSubscription != null) {
                        topicSubscriptionBean.remove(existingTopicSubscription);
                    }
                }

                postResponseDto.setSuccess(true);
                postResponseDto.setMessage(message);
                return postResponseDto;
            } else {
                message = "Unknown topic.";
            }
        } else {
            message = "Unidentified member.\nPlease log in.";
        }

        postResponseDto.setSuccess(false);
        postResponseDto.setMessage(message);
        return postResponseDto;
    }

    public PostResponseDto likePost(Member member, LikePostRequestDto requestDto) {
        PostResponseDto postResponseDto = new PostResponseDto();
        String message;
        int topicId = requestDto.getTopic();
        int postNumber = requestDto.getPostNumber();
        boolean isLikeRequest = requestDto.isLikeRequest();
        

        if (member != null) {
            Topic topic = topicBean.findEntityById(topicId);
            if (topic != null) {
                Post post = forumDao.getPostByPostNumber(topic, postNumber);
                if (post != null) {
                    MemberLike existingMemberLike = forumDao.getMemberLike(member, post);

                    if (isLikeRequest) {
                        message = "Post liked.";
                        if (existingMemberLike == null) {
                            MemberLike memberLike = new MemberLike();
                            memberLike.setMember(member);
                            memberLike.setPost(post);

                            memberLikeBean.add(memberLike);

                            //like notification
                            notificationService.addLike(member, post);

                        }

                    } else {
                        message = "Post unliked.";
                        if (existingMemberLike != null) {
                            memberLikeBean.remove(existingMemberLike);
                        }
                    }

                    postResponseDto.setSuccess(true);
                    postResponseDto.setMessage(message);
                    return postResponseDto;
                } else {
                    message = "Unknown post.";
                }
            } else {
                message = "Unknown topic.";
            }
        } else {
            message = "Unidentified member.\nPlease log in.";
        }

        postResponseDto.setSuccess(false);
        postResponseDto.setMessage(message);
        return postResponseDto;
    }
}
