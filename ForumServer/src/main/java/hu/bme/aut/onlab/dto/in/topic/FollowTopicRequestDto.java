package hu.bme.aut.onlab.dto.in.topic;

public class FollowTopicRequestDto {

    private int topic;
    private boolean isFollowRequest;

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public boolean getIsFollowRequest() {
        return isFollowRequest;
    }

    public void setIsFollowRequest(boolean isFollowRequest) {
        this.isFollowRequest = isFollowRequest;
    }
}
