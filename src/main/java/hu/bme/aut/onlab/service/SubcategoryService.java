package hu.bme.aut.onlab.service;

import hu.bme.aut.onlab.dao.ForumDao;
import hu.bme.aut.onlab.dao.NotificationsDao;
import hu.bme.aut.onlab.dao.model.*;
import hu.bme.aut.onlab.dto.in.subcategory.CreateTopicRequestDto;
import hu.bme.aut.onlab.dto.in.subcategory.FollowSubcategoryRequestDto;
import hu.bme.aut.onlab.dto.out.PostResponseDto;
import hu.bme.aut.onlab.dto.out.subcategory.CreateTopicResponseDto;
import hu.bme.aut.onlab.dto.out.subcategory.SubcategoryDto;
import hu.bme.aut.onlab.dto.out.subcategory.TopicSubcategoryDto;
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
public class SubcategoryService {

    @EJB
    private ForumDao forumDao;

    @EJB
    private SubcategoryBean subcategoryBean;

    @EJB
    private TopicBean topicBean;

    @EJB
    private PostBean postBean;

    @EJB
    private MemberDao memberBean;

    @EJB
    private NotificationsDao notificationService;

    @EJB
    private SubcategorySubscriptionBean subcategorySubscriptionBean;

    public SubcategoryDto getSubcategoryWithPage(Member member, int subcategoryId, int pageNumber) {
        SubcategoryDto subcategoryDto = new SubcategoryDto();

        Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
        boolean canMemberViewSubcategory = forumDao.canMemberViewSubcategory(member, subcategory);

        if (canMemberViewSubcategory) {
            List<Topic> topics = forumDao.getTopicsOnPage(subcategory, pageNumber);

            for (Topic topic : topics) {
                boolean isUnread = member == null ? false : forumDao.hasTopicUnreadPostsByMember(topic, member);
                Post firstPost = forumDao.getFirstPostFromTopic(topic);
                Member starterMemberPosted = firstPost.getMember();
                Post lastPost = forumDao.getLastPostFromTopic(topic);
                Member lastMemberPosted = lastPost.getMember();

                TopicSubcategoryDto topicSubcategoryDto = new TopicSubcategoryDto();
                topicSubcategoryDto.setUnread(isUnread);
                topicSubcategoryDto.setTitle(topic.getTitle());
                topicSubcategoryDto.setStarter(starterMemberPosted.getDisplayName());
                topicSubcategoryDto.setStartDate(Formatter.formatTimeStamp(firstPost.getTime()));
                topicSubcategoryDto.setPostCount(topic.getPosts().size());
                topicSubcategoryDto.setViewCount(topic.getViewCount());
                topicSubcategoryDto.setLastPoster(lastMemberPosted.getDisplayName());
                topicSubcategoryDto.setLastDate(Formatter.formatTimeStamp(lastPost.getTime()));
                topicSubcategoryDto.setTopicLink("#/topic/" + topic.getId());
                topicSubcategoryDto.setPostLink("#/topic/" + topic.getId() + "/" + lastPost.getPostNumber());
                topicSubcategoryDto.setStarterLink("#/user/" + starterMemberPosted.getId());
                topicSubcategoryDto.setPosterLink("#/user/" + lastMemberPosted.getId());
                topicSubcategoryDto.setPosterImageLink(LinkUtils.getProfilePictureLink(lastMemberPosted.getPictureId()));

                subcategoryDto.getTopics().add(topicSubcategoryDto);
            }

            subcategoryDto.setTitle(subcategory.getTitle());
            subcategoryDto.setIsFollowedByMember(forumDao.isMemberFollowingSubcategory(member, subcategory));
            subcategoryDto.setCanFollow((member != null));
            subcategoryDto.setCanStartTopic(forumDao.canMemberStartTopicInSubcategory(member, subcategory));
            subcategoryDto.setPages(NavigationUtils.getListOfPagesDto("#/category/" + subcategoryId, pageNumber, forumDao.getTopicCountInSubcategory(subcategory)));
        } else {
            subcategoryDto.setError(true);
        }

        return subcategoryDto;
    }

    public CreateTopicResponseDto createTopic(Member member, CreateTopicRequestDto requestDto) {
        CreateTopicResponseDto createTopicResponseDto = new CreateTopicResponseDto();
        String message;

        if (member != null) {
            int subcategoryId = requestDto.getCategory();
            Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
            if (subcategory != null) {
                if (forumDao.canMemberStartTopicInSubcategory(member, subcategory)) {
                    String subcategoryTitle = requestDto.getTitle();
                    String postText = requestDto.getText();

                    Topic topic = new Topic();
                    topic.setTitle(subcategoryTitle);
                    topic.setSubcategory(subcategory);
                    topic.setViewCount(0);
                    topicBean.add(topic);

                    Post post = new Post();
                    post.setTopic(topic);
                    post.setMember(member);
                    post.setText(postText);
                    post.setPostNumber(1);
                    post.setTime(Timestamp.valueOf(LocalDateTime.now()));
                    postBean.add(post);

                    member.setPostCount(member.getPostCount() + 1);
                    member.setTopicCount(member.getTopicCount() + 1);
                    memberBean.merge(member);

                    topicBean.flush();

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

                    //add subscribtion notification
                    notificationService.addNewTopic(topic);

                    forumDao.renewTopicSeenByMember(member, topic);

                    createTopicResponseDto.setSuccess(true);
                    createTopicResponseDto.setMessage("Topic created.");
                    createTopicResponseDto.setTopic(topic.getId());
                    return createTopicResponseDto;
                } else {
                    message = "You have no permission to start a new topic.";
                }
            } else {
                message = "Unknown category.";
            }
        } else {
            message = "Unidentified member.";
        }

        createTopicResponseDto.setSuccess(false);
        createTopicResponseDto.setMessage(message);
        return createTopicResponseDto;
    }

    public PostResponseDto followTopic(Member member, FollowSubcategoryRequestDto requestDto) {
        PostResponseDto postResponseDto = new PostResponseDto();
        String message;

        if (member != null) {
            int subcategoryId = requestDto.getCategory();
            Subcategory subcategory = subcategoryBean.findEntityById(subcategoryId);
            if (subcategory != null) {
                boolean isFollowRequest = requestDto.isFollowRequest();
                SubcategorySubscription existingSubcategorySubscription = forumDao.getSubcategorySubscription(member, subcategory);

                if (isFollowRequest) {
                    message = "Subcategory followed.";
                    if (existingSubcategorySubscription == null) {
                        SubcategorySubscription subcategorySubscription = new SubcategorySubscription();
                        subcategorySubscription.setMember(member);
                        subcategorySubscription.setSubcategory(subcategory);
                        subcategorySubscriptionBean.add(subcategorySubscription);
                    }

                } else {
                    message = "Subcategory unfollowed.";
                    if (existingSubcategorySubscription != null) {
                        subcategorySubscriptionBean.remove(existingSubcategorySubscription);
                    }
                }

                postResponseDto.setMessage(message);
                postResponseDto.setSuccess(true);
                return postResponseDto;
            } else {
                message = "Unknown category.";
            }
        } else {
            message = "Unidentified member.";
        }

        postResponseDto.setMessage(message);
        postResponseDto.setSuccess(true);
        return postResponseDto;
    }

}
