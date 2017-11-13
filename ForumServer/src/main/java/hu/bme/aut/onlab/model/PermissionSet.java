package hu.bme.aut.onlab.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "permission_set")
public class PermissionSet {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
	@Column(name = "title")
	private String title;
    
    @ManyToMany
    @JoinTable(name = "permission_set_to_member_group", 
    	joinColumns = @JoinColumn(name = "permission_set_id", referencedColumnName = "id", nullable = false), 
    	inverseJoinColumns = @JoinColumn(name = "member_group_id", referencedColumnName = "id", nullable = false))
	private List<MemberGroup> memberGroups;
    
    @ManyToMany(mappedBy = "permissionSets")
	private List<Permission> permissions;
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MemberGroup> getMemberGroups() {
        return memberGroups;
    }

    public void setMemberGroups(List<MemberGroup> memberGroups) {
        this.memberGroups = memberGroups;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
