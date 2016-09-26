package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
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
    private int memberGroupId;
    private Integer messageid;
    private Set<Conversation> conversations;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "display_name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Basic
    @Column(name = "post_count")
    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    @Basic
    @Column(name = "likes_count")
    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    @Basic
    @Column(name = "profile_views_count")
    public int getProfileViewsCount() {
        return profileViewsCount;
    }

    public void setProfileViewsCount(int profileViewsCount) {
        this.profileViewsCount = profileViewsCount;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "member_group_id")
    public int getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(int memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    @Basic
    @Column(name = "messageid")
    public Integer getMessageid() {
        return messageid;
    }

    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
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
        if (messageid != null ? !messageid.equals(member.messageid) : member.messageid != null) return false;

        return true;
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
        result = 31 * result + memberGroupId;
        result = 31 * result + (messageid != null ? messageid.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "members")
    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }
}
