package hu.bme.aut.onlab.dto.out.subcategory;

import hu.bme.aut.onlab.dto.out.BaseOutDto;
import hu.bme.aut.onlab.dto.out.PagesDto;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryDto extends BaseOutDto {

    private String title;
    private boolean isFollowedByMember;
    private boolean canFollow;
    private boolean canStartTopic;
    private List<TopicSubcategoryDto> topics;
    private List<PagesDto> pages;

    public SubcategoryDto() {
        super();
        topics = new ArrayList<>();
        pages = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsFollowedByMember() {
        return isFollowedByMember;
    }

    public void setIsFollowedByMember(boolean isFollowedByMember) {
        this.isFollowedByMember = isFollowedByMember;
    }

    public boolean getCanFollow() {
        return canFollow;
    }

    public void setCanFollow(boolean canFollow) {
        this.canFollow = canFollow;
    }

    public boolean getCanStartTopic() {
        return canStartTopic;
    }

    public void setCanStartTopic(boolean canStartTopic) {
        this.canStartTopic = canStartTopic;
    }

    public List<TopicSubcategoryDto> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicSubcategoryDto> topics) {
        this.topics = topics;
    }

    public List<PagesDto> getPages() {
        return pages;
    }

    public void setPages(List<PagesDto> pages) {
        this.pages = pages;
    }
}
