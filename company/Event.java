package company;

import java.time.LocalDateTime;
import java.util.List;

public interface Event extends Comparable<Event> {

	/**
	 * Get the host's email.
	 * 
	 * @return email.
	 */
	String getHost();

	/**
	 * Get the event's name.
	 * 
	 * @return name.
	 */
	String getName();

	/**
	 * Get the event's date.
	 * 
	 * @return date.
	 */
	LocalDateTime getDate();

	/**
	 * Get the event's priority.
	 * 
	 * @return priority.
	 */
	String getPriority();

	/**
	 * Get the event's topics.
	 * 
	 * @return topics.
	 */
	String getTopics();

	/**
	 * Get the number of people that have accepted the invitation.
	 * 
	 * @return number of accepted users.
	 */
	int getAcceptedSize();

	/**
	 * Get the number of people that have rejected the invitation.
	 * 
	 * @return number of rejected users.
	 */
	int getRejectedSize();

	/**
	 * Get the number of people that have not replied to the invitation.
	 * 
	 * @return number of unresponsive users.
	 */
	int getUnansweredSize();

	/**
	 * Get the number of people that have been invited to this event.
	 * 
	 * @return number of invited users.
	 */
	int getInvitedSize();

	/**
	 * Adds the host to the event list.
	 * 
	 * @param user: the host.
	 */
	void addHost(User user);

	/**
	 * Adds a user to the accepted list.
	 * 
	 * @param user: user that has accepted the invitation.
	 */
	void addUserToAccepted(User user);

	/**
	 * Removes a user from the accepted list.
	 * 
	 * @param user: user to be added.
	 */
	void removeUserFromAccepted(User user);

	/**
	 * Adds a user to the unanswered list.
	 * 
	 * @param user: user to be added.
	 */
	void addUserToUnanswered(User user);

	/**
	 * Removes a user from the unanswered list.
	 * 
	 * @param user: user to be removed.
	 */
	void removeUserFromUnanswered(User user);

	/**
	 * Adds a user to the rejected list.
	 * 
	 * @param user: user to be added.
	 */
	void addUserToRejected(User user);

	/**
	 * Adds a user to the invited list.
	 * 
	 * @param user: user to be added.
	 */
	void invite(User user);

	/**
	 * Checks if user has already been invited to the event.
	 * 
	 * @param user: user to check.
	 * @return true if user has already been invited, false otherwise.
	 */
	boolean hasUserBeenInvited(User user);

	/**
	 * Checks if user has already accepted the event.
	 * 
	 * @param user: user to check.
	 * @return true if user has already accepted, false otherwise.
	 */
	boolean hasUserAccepted(User user);

	/**
	 * Checks if user has already rejected the event.
	 * 
	 * @param user: user to check.
	 * @return true if user has already rejected, false otherwise.
	 */
	boolean hasUserRejected(User user);

	/**
	 * Returns the list of invited users.
	 * 
	 * @return invited list.
	 */
	List<User> getInvitedUsers();

	/**
	 * Get the number representing the status of the user. 
	 * 1 corresponds to accepted
	 * -1 corresponds to rejected
	 * 0 corresponds to unanswered
	 * 
	 * @param user: user to check.
	 * @return number representing user status
	 */
	int eventStatus(User user);
}
