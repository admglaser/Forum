package hu.bme.aut.onlab.dto.out.subcategory;

public class TopicSubcategoryDto {

    private String postLink;
    private String posterImageLink;
    private String starter;
    private String starterLink;
    private String starterStyle;
    private String topicLink;
    private String title;
    private String posterLink;
    private String lastPoster;
    private String lastPosterStyle;
    private String startDate;
    private String lastDate;
    private boolean unread;
    private int postCount;
    private int viewCount;

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getPosterImageLink() {
        return posterImageLink;
    }

    public void setPosterImageLink(String posterImageLink) {
        this.posterImageLink = posterImageLink;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getStarterLink() {
        return starterLink;
    }

    public void setStarterLink(String starterLink) {
        this.starterLink = starterLink;
    }

    public String getTopicLink() {
        return topicLink;
    }

    public void setTopicLink(String topicLink) {
        this.topicLink = topicLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getLastPoster() {
        return lastPoster;
    }

    public void setLastPoster(String lastPoster) {
        this.lastPoster = lastPoster;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public boolean getUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

	public String getStarterStyle() {
		return starterStyle;
	}

	public void setStarterStyle(String starterStyle) {
		this.starterStyle = starterStyle;
	}

	public String getLastPosterStyle() {
		return lastPosterStyle;
	}

	public void setLastPosterStyle(String lastPosterStyle) {
		this.lastPosterStyle = lastPosterStyle;
	}
}
