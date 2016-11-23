package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "member_group")
public class MemberGroup {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
	@Column(name = "title")
    private String title;
    
    @OneToMany(mappedBy = "memberGroup")
    private Collection<Member> members;
    
    @ManyToMany(mappedBy = "memberGroups")
    private List<PermissionSet> permissionSets;

    @OneToMany(mappedBy = "memberGroup")
    private List<StyleOfMemberGroup> styleOfMemberGroups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<Member> getMembers() {
        return members;
    }

    public void setMembers(Collection<Member> members) {
        this.members = members;
    }

    public List<PermissionSet> getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(List<PermissionSet> permissionSets) {
        this.permissionSets = permissionSets;
    }

    public List<StyleOfMemberGroup> getStyleOfMemberGroups() {
        return styleOfMemberGroups;
    }

    public void setStyleOfMemberGroups(List<StyleOfMemberGroup> styleOfMemberGroupsById) {
        this.styleOfMemberGroups = styleOfMemberGroupsById;
    }
    
}
