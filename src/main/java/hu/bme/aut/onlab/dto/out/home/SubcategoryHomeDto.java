package hu.bme.aut.onlab.dto.out.home;

public class SubcategoryHomeDto {

	private String postLink;
	private String subcategoryLink;
	private String lastTitle;
	private boolean unread;
	private String title;
	private int topicCount;
	private int postCount;
	private boolean hasLastTopic;
	private String lastPosterImageLink;
	private String lastPoster;
	private String desc;
	private String lastDate;
	private String userLink;

	public String getPostLink() {
		return postLink;
	}

	public void setPostLink(String postLink) {
		this.postLink = postLink;
	}

	public String getSubcategoryLink() {
		return subcategoryLink;
	}

	public void setSubcategoryLink(String subcategoryLink) {
		this.subcategoryLink = subcategoryLink;
	}

	public String getLastTitle() {
		return lastTitle;
	}

	public void setLastTitle(String lastTitle) {
		this.lastTitle = lastTitle;
	}

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public boolean isHasLastTopic() {
		return hasLastTopic;
	}

	public void setHasLastTopic(boolean hasLastTopic) {
		this.hasLastTopic = hasLastTopic;
	}

	public String getLastPosterImageLink() {
		return lastPosterImageLink;
	}

	public void setLastPosterImageLink(String lastPosterImageLink) {
		this.lastPosterImageLink = lastPosterImageLink;
	}

	public String getLastPoster() {
		return lastPoster;
	}

	public void setLastPoster(String lastPoster) {
		this.lastPoster = lastPoster;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getUserLink() {
		return userLink;
	}

	public void setUserLink(String userLink) {
		this.userLink = userLink;
	}

}
