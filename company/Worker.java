package company;

import java.time.LocalDateTime;

public interface Worker {
	
	/**
	 * Adds new event. The host is automatically added
	 * to the accepted list. If an event with the same
	 * name or date hasn't been created yet, new lists are created.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @param priority: event's priority.
	 * @param topics: event's topics.
	 * @param date: event's date.
	 * @pre email != null && eventName != null &&
	 * 		priority != null && topics != null && date != null
	 */
	void addEvent(String email, String eventName, String priority, String topics, LocalDateTime date);

}
