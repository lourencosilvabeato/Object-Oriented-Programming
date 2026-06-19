package company;

import java.util.Iterator;
import java.time.LocalDateTime;
import exceptions.*;

public interface CalendarApp {

	/**
	 * Registers a new account into the system.
	 * @param email: new user's email.
	 * @param type: new user's account type.
	 * @throws AccountNameExistsException if userExists(email).
	 * @throws UnknownAccountTypeException if UserType.convert(type) == null.
	 */
	void register(String email, String type) throws AccountNameExistsException, UnknownAccountTypeException;

	/**
	 * Get all users iterator.
	 * @return iterator.
	 */
	Iterator<User> allUsersIterator();

	/**
	 * Checks if there are any registered users.
	 * @return true if there are, false otherwise.
	 */
	boolean hasUsers();

	/**
	 * Adds new event to the system and to the host's list.
	 * @param email: host's email.
	 * @param eventName: new event's name.
	 * @param priority: new event's priority.
	 * @param date: new event's date.
	 * @param topics: new event's topics.
	 * @throws AccountNameDoesNotExistException if !userExists(email).
	 * @throws EventPriorityUnknownException if priority isn't high or mid.
	 * @throws CannotCreateEventsException if the host is a guest.
	 * @throws CannotCreateHighEventsException if priority is high and the
	 * 			host is a staff member.
	 * @throws SameEventNameException if the host has already created an event
	 * 			with that name.
	 * @throws SameTimeEventException if the host has already accepted an event
	 * 			with that date.
	 */
	void addEvent(String email, String eventName, String priority, LocalDateTime date, String topics)
			throws AccountNameDoesNotExistException, EventPriorityUnknownException, CannotCreateEventsException,
			CannotCreateHighEventsException, SameEventNameException, SameTimeEventException;

	/**
	 * Get a user's all events iterator.
	 * @param email: user's email.
	 * @return iterator.
	 */
	Iterator<Event> allEventsIterator(String email);

	/**
	 * Checks if user has any events.
	 * @throws AccountNameDoesNotExistException if !userExists(email).
	 * @return true if the user has any, false otherwise.
	 */
	boolean hasEvents(String email) throws AccountNameDoesNotExistException;

	/**
	 * Invites a new user to the host's event.
	 * @param invitedEmail: invited user's email.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @throws AccountNameDoesNotExistException if !userExists(email) || !userExists(invitedEmail).
	 * @throws HostDoesNotHaveEventException if the host doesn't have an event with that name.
	 * @throws AlreadyInvitedException if the invited user has already been invited.
	 * @throws SameTimeEventException if the invited user has another event at the same time.
	 */
	void invite(String invitedEmail, String email, String eventName) throws AccountNameDoesNotExistException,
			HostDoesNotHaveEventException, AlreadyInvitedException, SameTimeEventException;

	/**
	 * Removes deleted event from all users.
	 * @param invitedEmail: invited user's email.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre invitedEmail != null && email != null && eventName != null && event exists.
	 */
	void removeOldHost(String invitedEmail, String email, String eventName);
	
	/**
	 * Get the name of the name of the old event hosted by
	 * the invited user.
	 * @param invitedEmail: invited user's email.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre invitedEmail != null && email != null && eventName != null && event exists.
	 * @return event name.
	 */
	String getHostEventName(String invitedEmail, String email, String eventName);

	/**
	 * Checks if the event with a certain host and name is high priority.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre email != null && eventName != null && event exists.
	 * @return true if the event is high priority, false otherwise.
	 */
	boolean isHighPriority(String email, String eventName);

	/**
	 * Checks if the user with that email is a staff member.
	 * @param email: user's email.
	 * @pre email != null.
	 * @return true if the user is a staff member, false otherwise.
	 */
	boolean isStaffMember(String invitedEmail);

	/**
	 * Checks if the user is the host of an event with a certain date.
	 * @param invitedEmail: invited user's email.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre invitedEmail != null && email != null && eventName != null && event exists.
	 * @return true if the user is the host, false otherwise.
	 */
	boolean isHost(String invitedEmail, String email, String eventName);

	/**
	 * Checks if the invited user has already been invited to this event.
	 * @param invitedEmail: invited user's email.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre invitedEmail != null && email != null && eventName != null && event exists.
	 * @return true if the user has already been invited, false otherwise.
	 */
	boolean alreadyHasSameEvent(String invitedEmail, String email, String eventName);
	
	/**
	 * Checks if the invited user already has an event occurring at the same time.
	 * If the invited user has already accepted an event with this date and one of
	 * the following is true:
	 * 1) host event is mid priority
	 * 2) host event is high priority and invited user is not a staff member
	 * 3) host event is high priority and invited user is a staff member with
	 *    a high priority event.
	 * @param invitedEmail: invited user's email.
	 * @param email: user's email.
	 * @param eventName: event's name.
	 * @pre email != null && invitedEmail != null && eventName != null.
	 * @return true if any of these conditions are met, false otherwise.
	 */
	boolean hasOtherEvent(String invitedEmail, String email, String eventName);

	/**
	 * Get a user's rejected events iterator.
	 * @param invitedEmail: invited user's email.
	 * @param email: user's email.
	 * @param eventName: event's name.
	 * @pre email != null && invitedEmail != null && eventName != null.
	 * @return iterator.
	 */
	Iterator<Event> rejectedEventsList(String invitedEmail, String email, String eventName);

	/**
	 * The invited user responds to the host's invitation.
	 * @param invitedEmail: invited user's email.
	 * @param email: user's email.
	 * @param eventName: event's name.
	 * @param answer: user's answer.
	 * @throws AccountNameDoesNotExistException if !userExists(email) || !userExists(invitedEmail).
	 * @throws InvalidAnswerException if the answer is not "accept" or "reject".
	 * @throws HostDoesNotHaveEventException if the host doesn't have an event with that name.
	 * @throws NotInvitedException if the invited user was not invited to the event.
	 * @throws AlreadyRespondedException if the invited user has already been responded to the invitation.
	 * @throws SameTimeEventException if the invited user has another event at the same time.
	 */
	void answer(String invitedEmail, String email, String eventName, String answer)
			throws AccountNameDoesNotExistException, InvalidAnswerException, HostDoesNotHaveEventException,
			NotInvitedException, AlreadyRespondedException, SameTimeEventException;

	/**
	 * Get the date of an event by its host and name.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre email != null && eventName != null.
	 * @return date of the event.
	 */
	LocalDateTime getEventDate(String email, String eventName);

	/**
	 * Checks if the host has an event with a certain date.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @pre email != null && eventName != null.
	 * @return true if the date exists, false otherwise.
	 */
	boolean hasDate(String email, String eventName);
	
	/**
	 * Get all users invited to the event (with a certain email and name) iterator.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @throws AccountNameDoesNotExistException if !userExists(email)
	 * @throws HostDoesNotHaveEventException if the host doesn't have an event with that name.
	 * @return iterator.
	 */
	Iterator<User> eventsByTopicsIterator(String email, String eventName)
			throws AccountNameDoesNotExistException, HostDoesNotHaveEventException;

	/**
	 * Get the status of the user.
	 * @param email: host's email.
	 * @param eventName: event's name.
	 * @param user: user to check the status.
	 * @pre email != null && eventName != null && userExists(email).
	 * @return status.
	 */
	String getUserStatus(String email, String eventName, User user);

	/**
	 * Get the events containing at least one of the requested topics.
	 * @param topics: array of requested topics.
	 * @return iterator.
	 */
	Iterator<Event> eventsByTopicsIterator(String[] topics);

	/**
	 * Checks if there are any events with at least one of
	 * the requested topics
	 * @param topics: array of requested topics.
	 * @return true if there are any, false otherwise.
	 */
	boolean hasEventsForTopics(String[] topics);
}
