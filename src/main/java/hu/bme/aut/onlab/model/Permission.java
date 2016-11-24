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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "read_allowed")
	private boolean readAllowed;
    
	@Column(name = "reply_allowed")
	private boolean replyAllowed;
    
	@Column(name = "start_allowed")
	private boolean startAllowed;
    
    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
	private Subcategory subcategory;
    
    @ManyToMany
    @JoinTable(name = "permission_to_permission_set", catalog = "forum", schema = "", 
    	joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false), 
    	inverseJoinColumns = @JoinColumn(name = "permission_set_id", referencedColumnName = "id", nullable = false))
	private List<PermissionSet> permissionSets;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getReadAllowed() {
        return readAllowed;
    }

    public void setReadAllowed(boolean readAllowed) {
        this.readAllowed = readAllowed;
    }

    public boolean getReplyAllowed() {
        return replyAllowed;
    }

    public void setReplyAllowed(boolean replyAllowed) {
        this.replyAllowed = replyAllowed;
    }

    public boolean getStartAllowed() {
        return startAllowed;
    }

    public void setStartAllowed(boolean startAllowed) {
        this.startAllowed = startAllowed;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public List<PermissionSet> getPermissionSets() {
        return permissionSets;
    }

    public void setPermissionSets(List<PermissionSet> permissionSets) {
        this.permissionSets = permissionSets;
    }

}
