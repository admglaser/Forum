package hu.bme.aut.onlab.model;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "notification_event")
public class NotificationEvent {
  
	@Id
	@Column(name = "id")
	private int id;
    
	private int type;
    
	private Timestamp time;
    
	private String link;
    
	@OneToMany(mappedBy = "notificationEvent")
	private Collection<Notification> notifications;
    
	private String text;

	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
