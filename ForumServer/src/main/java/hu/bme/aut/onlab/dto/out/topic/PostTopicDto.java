package hu.bme.aut.onlab.dto.out.topic;

import java.util.ArrayList;
import java.util.List;

public class PostTopicDto {

    private String username;
    private String userLink;
    private String userImageLink;
    private String postLink;
    private String memberGroup;
    private String time;
    private String text;
    private String likers;
    private int postCount;
    private int likeCount;
    private int postNumber;
    private boolean isPostLiked;
    private List<String> styles;

    public PostTopicDto() {
        styles = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }

    public String getUserImageLink() {
        return userImageLink;
    }

    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(String memberGroup) {
        this.memberGroup = memberGroup;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public boolean getIsPostLiked() {
        return isPostLiked;
    }

    public void setIsPostLiked(boolean isPostLiked) {
        this.isPostLiked = isPostLiked;
    }

    public List<String> getStyles() {
        return styles;
    }

    public void setStyles(List<String> styles) {
        this.styles = styles;
    }

    public String getLikers() {
        return likers;
    }

    public void setLikers(String likers) {
        this.likers = likers;
    }
}
