package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "member_group", schema = "", catalog = "forum")
public class MemberGroup {
    private int id;
    private String title;
    private String prefix;
    private String postfix;
    private Collection<Member> membersById;
    private Set<PermissionSet> permissionSets;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "prefix", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "postfix", nullable = true, insertable = true, updatable = true, length = 255)
    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberGroup that = (MemberGroup) o;

        if (id != that.id) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (postfix != null ? !postfix.equals(that.postfix) : that.postfix != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (postfix != null ? postfix.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "memberGroupByMemberGroupId")
    public Collection<Member> getMembersById() {
        return membersById;
    }

    public void setMembersById(Collection<Member> membersById) {
        this.membersById = membersById;
    }

    @ManyToMany(mappedBy = "memberGroups")
    public Set<PermissionSet> getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(Set<PermissionSet> permissionSets) {
        this.permissionSets = permissionSets;
    }
}
