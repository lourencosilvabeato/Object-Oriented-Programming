package company;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public interface User {

	/**
	 * Get the user's email.
	 * 
	 * @return email.
	 */
	String getEmail();

	/**
	 * Get all event's iterator.
	 * 
	 * @return iterator.
	 */
	Iterator<Event> allEventsIterator();

	/**
	 * Checks if user has any events.
	 * 
	 * @return true if the user has any events, false otherwise.
	 */
	boolean hasEvents();

	/**
	 * Get the event belonging to a certain host by its name.
	 * 
	 * @param email:     event's host email.
	 * @param eventName: event's name.
	 * @pre email != null && eventName != null
	 * @return event.
	 */
	Event getEventByName(String email, String eventName);

	/**
	 * Get the event belonging to a certain host by its date.
	 * 
	 * @param email: event's host email.
	 * @param date:  event's date.
	 * @pre email != null && date != null
	 * @return event.
	 */
	Event getEventByDate(String email, LocalDateTime date);

	/**
	 * Get an event's priority, based on its date
	 * 
	 * @param date: event's date.
	 * @pre date != null
	 * @return priority.
	 */
	String getEventPriority(LocalDateTime date);

	/**
	 * Get the date of an event belonging to a certain host by its name.
	 * 
	 * @param email:     event's host email.
	 * @param eventName: event's name.
	 * @pre email != null && eventName != null
	 * @return date.
	 */
	LocalDateTime getEventDate(String email, String eventName);

	/**
	 * Checks if user has already accepted the event.
	 * 
	 * @param event: event which the user has been invited to.
	 * @pre event != null.
	 * @return true if the user has already accepted the event, false otherwise.
	 */
	boolean alreadyAcceptedEvent(Event event);

	/**
	 * Checks if user has already accepted an event, with a certain date.
	 * 
	 * @param date: date of the event to check.
	 * @pre date != null.
	 * @return true if the user has already accepted an event with that date, false
	 *         otherwise.
	 */
	boolean alreadyAcceptedEvent(LocalDateTime date);

	/**
	 * Checks if user has already accepted the invitation for this same event (event
	 * with certain host and date).
	 * 
	 * @param email: event's host email.
	 * @param date:  date of the event.
	 * @pre email != null && event != null.
	 * @return true if the user has already accepted this event, false otherwise.
	 */
	boolean alreadyHasSameEvent(String email, LocalDateTime date);

	/**
	 * Checks if user has not yet replied to the event invitation.
	 * 
	 * @param event: event which the user has been invited to.
	 * @pre event != null.
	 * @return true if the user hasn't responded, false otherwise.
	 */
	boolean unansweredEvent(Event event);

	/**
	 * Checks if user has not yet replied to the event invitation.
	 * 
	 * @param email: event's host email.
	 * @param date:  date of the event.
	 * @pre email != null && event != null.
	 * @return true if the user hasn't responded, false otherwise.
	 */
	boolean unansweredEvent(String email, LocalDateTime date);

	/**
	 * The event is removed (canceled by the host).
	 * 
	 * @param event: event to remove.
	 * @pre event != null && has the event.
	 */
	void removeEventFromUser(Event event);

	/**
	 * The user is invited to the event. Therefore, the event's list is updated and
	 * the user's event list as well.
	 * 
	 * @param event: event the user is invited to.
	 * @pre event != null
	 */
	void invite(Event event);

	/**
	 * Responds to event invitation based on the answer.
	 * 
	 * @param event:  event the user is responding to.
	 * @param answer: response to give (accept or reject).
	 * @pre event != null && answer != null
	 */
	void response(Event event, String answer);

	/**
	 * Get list of all events that occur at the same time.
	 * 
	 * @param date: date of the event.
	 * @pre date != null.
	 * @return list of same time events.
	 */
	List<Event> sameTimeEvents(LocalDateTime date);

	/**
	 * Removes all events that occur at the same time (except the new event).
	 * 
	 * @param newEvent: new event the user is attending.
	 * @param date:     date of the event.
	 * @pre newEvent != null && event != null.
	 */
	void removeSameTimeEvents(Event newEvent, LocalDateTime date);
}
