package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "permission_set", schema = "", catalog = "forum")
public class PermissionSet {
    private int id;
    private int permissionId;
    private Set<MemberGroup> memberGroups;
    private Set<Permission> permissions;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionSet that = (PermissionSet) o;

        if (id != that.id) return false;
        return permissionId == that.permissionId;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + permissionId;
        return result;
    }

    @ManyToMany
    @JoinTable(name = "permission_set_to_member_group", catalog = "forum", schema = "", joinColumns = @JoinColumn(name = "permission_set_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "member_group_id", referencedColumnName = "id", nullable = false))
    public Set<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public void setMemberGroups(Set<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    @ManyToMany(mappedBy = "permissionSets")
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Basic
    @Column(name = "permission_id", nullable = false, insertable = true, updatable = true)
    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }
}
