package hu.bme.aut.onlab.dto.out.topic;

import hu.bme.aut.onlab.dto.out.BaseOutDto;
import hu.bme.aut.onlab.dto.out.PagesDto;

import java.util.ArrayList;
import java.util.List;

public class TopicDto extends BaseOutDto {

    private String title;
    private String startedByText;
    private boolean isFollowedByMember;
    private boolean canFollow;
    private boolean canReply;
    private List<PostTopicDto> posts;
    private List<PagesDto> pages;

    public TopicDto() {
        super();

        posts = new ArrayList<>();
        pages = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartedByText() {
        return startedByText;
    }

    public void setStartedByText(String startedByText) {
        this.startedByText = startedByText;
    }

    public boolean isFollowedByMember() {
        return isFollowedByMember;
    }

    public void setIsFollowedByMember(boolean isFollowedByMember) {
        this.isFollowedByMember = isFollowedByMember;
    }

    public boolean isCanFollow() {
        return canFollow;
    }

    public void setCanFollow(boolean canFollow) {
        this.canFollow = canFollow;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public List<PostTopicDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostTopicDto> posts) {
        this.posts = posts;
    }

    public List<PagesDto> getPages() {
        return pages;
    }

    public void setPages(List<PagesDto> pages) {
        this.pages = pages;
    }
}
