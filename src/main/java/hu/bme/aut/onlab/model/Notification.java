package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "notification")
public class Notification {
    private int id;
    private byte seen;
    private Member memberByMemberId;
    private NotificationEvent notificationEventByNotificationEventId;
    private int memberId;
    private int notificationEventId;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "seen", nullable = false, insertable = true, updatable = true)
    public byte getSeen() {
        return seen;
    }

    public void setSeen(byte seen) {
        this.seen = seen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (id != that.id) return false;
        if (seen != that.seen) return false;
        if (memberId != that.memberId) return false;
        return notificationEventId == that.notificationEventId;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) seen;
        result = 31 * result + memberId;
        result = 31 * result + notificationEventId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }

    @ManyToOne
    @JoinColumn(name = "notification_event_id", referencedColumnName = "id", nullable = false)
    public NotificationEvent getNotificationEventByNotificationEventId() {
        return notificationEventByNotificationEventId;
    }

    public void setNotificationEventByNotificationEventId(NotificationEvent notificationEventByNotificationEventId) {
        this.notificationEventByNotificationEventId = notificationEventByNotificationEventId;
    }

    @Basic
    @Column(name = "member_id", nullable = false, insertable = false, updatable = false)
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "notification_event_id", nullable = false, insertable = false, updatable = false)
    public int getNotificationEventId() {
        return notificationEventId;
    }

    public void setNotificationEventId(int notificationEventId) {
        this.notificationEventId = notificationEventId;
    }
}
