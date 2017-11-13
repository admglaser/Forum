package hu.bme.aut.onlab.dto.in.topic;

public class LikePostRequestDto {

    private int topic;
    private int postNumber;
    private boolean isLikeRequest;

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public boolean getIsLikeRequest() {
        return isLikeRequest;
    }

    public void setIsLikeRequest(boolean isLikeRequest) {
        this.isLikeRequest = isLikeRequest;
    }
}
