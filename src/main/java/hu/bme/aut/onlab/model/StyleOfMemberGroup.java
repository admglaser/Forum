package hu.bme.aut.onlab.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "style_of_member_group")
public class StyleOfMemberGroup {
    private int id;
    private String style;
    private Integer memberGroupId;
    private MemberGroup memberGroup;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "style", nullable = true, insertable = true, updatable = true, length = 255)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Basic
    @Column(name = "member_group_id", nullable = true, insertable = false, updatable = false)
    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    @ManyToOne
    @JoinColumn(name = "member_group_id", referencedColumnName = "id")
    public MemberGroup getMemberGroup() {
        return memberGroup;
    }

    public void setMemberGroup(MemberGroup memberGroupByMemberGroupId) {
        this.memberGroup = memberGroupByMemberGroupId;
    }
}
