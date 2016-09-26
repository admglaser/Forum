package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
@Table(name = "permission")
public class Permission {
    private int id;
    private byte readAllowed;
    private byte replyAllowed;
    private byte startAllowed;
    private int subcategoryId;
    private Set<PermissionSet> permissionSets;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "read_allowed")
    public byte getReadAllowed() {
        return readAllowed;
    }

    public void setReadAllowed(byte readAllowed) {
        this.readAllowed = readAllowed;
    }

    @Basic
    @Column(name = "reply_allowed")
    public byte getReplyAllowed() {
        return replyAllowed;
    }

    public void setReplyAllowed(byte replyAllowed) {
        this.replyAllowed = replyAllowed;
    }

    @Basic
    @Column(name = "start_allowed")
    public byte getStartAllowed() {
        return startAllowed;
    }

    public void setStartAllowed(byte startAllowed) {
        this.startAllowed = startAllowed;
    }

    @Basic
    @Column(name = "subcategory_id")
    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (id != that.id) return false;
        if (readAllowed != that.readAllowed) return false;
        if (replyAllowed != that.replyAllowed) return false;
        if (startAllowed != that.startAllowed) return false;
        if (subcategoryId != that.subcategoryId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) readAllowed;
        result = 31 * result + (int) replyAllowed;
        result = 31 * result + (int) startAllowed;
        result = 31 * result + subcategoryId;
        return result;
    }

    @ManyToMany
    @JoinTable(name = "permission_to_permission_set", catalog = "forum", schema = "", joinColumns = @JoinColumn(name = "permissionid", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "permission_setid", referencedColumnName = "id", nullable = false))
    public Set<PermissionSet> getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(Set<PermissionSet> permissionSets) {
        this.permissionSets = permissionSets;
    }
}
