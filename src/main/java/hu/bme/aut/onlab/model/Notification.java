package hu.bme.aut.onlab.model;

import javax.persistence.*;

/**
 * Created by Logan on 2016.09.17..
 */
@Entity
@Table(name = "notification")
public class Notification {
    private int id;
    private byte seen;
    private int memberId;
    private int notificationEventId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "seen")
    public byte getSeen() {
        return seen;
    }

    public void setSeen(byte seen) {
        this.seen = seen;
    }

    @Basic
    @Column(name = "member_id")
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "notification_event_id")
    public int getNotificationEventId() {
        return notificationEventId;
    }

    public void setNotificationEventId(int notificationEventId) {
        this.notificationEventId = notificationEventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (id != that.id) return false;
        if (seen != that.seen) return false;
        if (memberId != that.memberId) return false;
        if (notificationEventId != that.notificationEventId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) seen;
        result = 31 * result + memberId;
        result = 31 * result + notificationEventId;
        return result;
    }
}
