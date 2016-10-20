package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "member")
public class Member {
    private int id;
    private String userName;
    private String password;
    private String email;
    private String displayName;
    private int postCount;
    private int likesCount;
    private int profileViewsCount;
    private Date birthday;
    private Collection<Like> likesById;
    private MemberGroup memberGroupByMemberGroupId;
    private Collection<Notification> notificationsById;
    private Collection<Post> postsById;
    private Collection<SubcategorySubscription> subcategorySubscriptionsById;
    private Collection<TopicSubscription> topicSubscriptionsById;
    private Set<Conversation> conversations;
    private Timestamp registerTime;
    private int memberGroupId;
    private String pictureId;
    private Collection<ConversationSeenByMember> conversationSeenByMembersById;
    private Collection<TopicSeenByMember> topicSeenByMembersById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "display_name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "post_count", nullable = false, insertable = true, updatable = true)
    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    @Basic
    @Column(name = "likes_count", nullable = false, insertable = true, updatable = true)
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    @Basic
    @Column(name = "profile_views_count", nullable = false, insertable = true, updatable = true)
    public int getProfileViewsCount() {
        return profileViewsCount;
    }

    public void setProfileViewsCount(int profileViewsCount) {
        this.profileViewsCount = profileViewsCount;
    }

    @Basic
    @Column(name = "birthday", nullable = true, insertable = true, updatable = true)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<Like> getLikesById() {
        return likesById;
    }

    public void setLikesById(Collection<Like> likesById) {
        this.likesById = likesById;
    }

    @ManyToOne
    @JoinColumn(name = "member_group_id", referencedColumnName = "id", nullable = false)
    public MemberGroup getMemberGroupByMemberGroupId() {
        return memberGroupByMemberGroupId;
    }

    public void setMemberGroupByMemberGroupId(MemberGroup memberGroupByMemberGroupId) {
        this.memberGroupByMemberGroupId = memberGroupByMemberGroupId;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<Notification> getNotificationsById() {
        return notificationsById;
    }

    public void setNotificationsById(Collection<Notification> notificationsById) {
        this.notificationsById = notificationsById;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<Post> getPostsById() {
        return postsById;
    }

    public void setPostsById(Collection<Post> postsById) {
        this.postsById = postsById;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<SubcategorySubscription> getSubcategorySubscriptionsById() {
        return subcategorySubscriptionsById;
    }

    public void setSubcategorySubscriptionsById(Collection<SubcategorySubscription> subcategorySubscriptionsById) {
        this.subcategorySubscriptionsById = subcategorySubscriptionsById;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<TopicSubscription> getTopicSubscriptionsById() {
        return topicSubscriptionsById;
    }

    public void setTopicSubscriptionsById(Collection<TopicSubscription> topicSubscriptionsById) {
        this.topicSubscriptionsById = topicSubscriptionsById;
    }

    @ManyToMany(mappedBy = "members")
    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (id != member.id) return false;
        if (postCount != member.postCount) return false;
        if (likesCount != member.likesCount) return false;
        if (profileViewsCount != member.profileViewsCount) return false;
        if (memberGroupId != member.memberGroupId) return false;
        if (userName != null ? !userName.equals(member.userName) : member.userName != null) return false;
        if (password != null ? !password.equals(member.password) : member.password != null) return false;
        if (email != null ? !email.equals(member.email) : member.email != null) return false;
        if (displayName != null ? !displayName.equals(member.displayName) : member.displayName != null) return false;
        if (birthday != null ? !birthday.equals(member.birthday) : member.birthday != null) return false;
        if (registerTime != null ? !registerTime.equals(member.registerTime) : member.registerTime != null)
            return false;
        return !(pictureId != null ? !pictureId.equals(member.pictureId) : member.pictureId != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + postCount;
        result = 31 * result + likesCount;
        result = 31 * result + profileViewsCount;
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
        result = 31 * result + memberGroupId;
        result = 31 * result + (pictureId != null ? pictureId.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "register_time", nullable = false, insertable = true, updatable = true)
    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    @Basic
    @Column(name = "member_group_id", nullable = false, insertable = false, updatable = false)
    public int getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(int memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    @Basic
    @Column(name = "picture_id", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<ConversationSeenByMember> getConversationSeenByMembersById() {
        return conversationSeenByMembersById;
    }

    public void setConversationSeenByMembersById(Collection<ConversationSeenByMember> conversationSeenByMembersById) {
        this.conversationSeenByMembersById = conversationSeenByMembersById;
    }

    @OneToMany(mappedBy = "memberByMemberId")
    public Collection<TopicSeenByMember> getTopicSeenByMembersById() {
        return topicSeenByMembersById;
    }

    public void setTopicSeenByMembersById(Collection<TopicSeenByMember> topicSeenByMembersById) {
        this.topicSeenByMembersById = topicSeenByMembersById;
    }
}
