package hu.bme.aut.onlab.dto.in.subcategory;

public class FollowSubcategoryRequestDto {

    private int category;
    private boolean isFollowRequest;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isFollowRequest() {
        return isFollowRequest;
    }

    public void setIsFollowRequest(boolean isFollowRequest) {
        this.isFollowRequest = isFollowRequest;
    }
}
