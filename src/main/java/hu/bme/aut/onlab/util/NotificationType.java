package hu.bme.aut.onlab.util;

public enum NotificationType {

	QUOTE(0, "Quote"), 
	MENTION(1, "Mention"), 
	NEW_REPLY(2, "New reply"), 
	NEW_TOPIC(3, "New topic"),
	LIKE(4, "Like");
	
	private int id;
	private String string;

	private NotificationType(int id, String string) {
		this.id = id;
		this.string = string;
	}

	public int getId() {
		return id;
	}

	public String getString() {
		return string;
	}
	
	@Override
	public String toString() {
		return string;
	}

	public static NotificationType getNotificationType(int id) {
		switch (id) {
		case 0:
			return NotificationType.QUOTE;
		case 1:
			return NotificationType.MENTION;
		case 2:
			return NotificationType.NEW_REPLY;
		case 3:
			return NotificationType.NEW_TOPIC;
		case 4:
			return NotificationType.LIKE;
		default:
			throw new RuntimeException("Unhandled notification id!");
		}
	}

}
