package hu.bme.aut.onlab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
   
	@Id
	@Column(name = "id")
	private int id;
    
	private int notificationNumber;
    
	private boolean seen;
    
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "notification_event_id", referencedColumnName = "id", nullable = false)
	private NotificationEvent notificationEvent;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(int notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Member getMemberByMemberId() {
        return member;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.member = memberByMemberId;
    }

    public NotificationEvent getNotificationEvent() {
        return notificationEvent;
    }

    public void setNotificationEvent(NotificationEvent notificationEvent) {
        this.notificationEvent = notificationEvent;
    }

}
