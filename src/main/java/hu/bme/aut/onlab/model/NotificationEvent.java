package hu.bme.aut.onlab.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by N. Vilagos.
 */
@Entity
@Table(name = "notification_event", schema = "", catalog = "forum")
public class NotificationEvent {
    private int id;
    private int type;
    private Timestamp time;
    private String link;
    private Collection<Notification> notificationsById;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type", nullable = false, insertable = true, updatable = true)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "link", nullable = false, insertable = true, updatable = true, length = 255)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationEvent that = (NotificationEvent) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return !(notificationsById != null ? !notificationsById.equals(that.notificationsById) : that.notificationsById != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (notificationsById != null ? notificationsById.hashCode() : 0);
        return result;
    }



    @OneToMany(mappedBy = "notificationEventByNotificationEventId")
    public Collection<Notification> getNotificationsById() {
        return notificationsById;
    }

    public void setNotificationsById(Collection<Notification> notificationsById) {
        this.notificationsById = notificationsById;
    }

}
