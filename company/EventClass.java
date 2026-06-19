package company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class EventClass implements Event {

	private List<User> invited;
	private List<User> rejected;
	private List<User> accepted;
	private List<User> unanswered;
	private String host;
	private String name;
	private LocalDateTime date;
	private String priority;
	private String topics;

	EventClass(String host, String name, LocalDateTime date, String priority, String topics) {
		invited = new ArrayList<User>();
		rejected = new ArrayList<User>();
		accepted = new ArrayList<User>();
		unanswered = new ArrayList<User>();
		this.host = host;
		this.name = name;
		this.date = date;
		this.priority = priority;
		this.topics = topics;
	}

	public String getHost() {
		return host;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getPriority() {
		return priority;
	}

	public String getTopics() {
		return topics;
	}

	public int getAcceptedSize() {
		return accepted.size();
	}

	public int getRejectedSize() {
		return rejected.size();
	}

	public int getUnansweredSize() {
		return unanswered.size();
	}

	public int getInvitedSize() {
		return invited.size();
	}

	public void addHost(User user) {
		invited.add(user);
		accepted.add(user);
	}

	public void addUserToAccepted(User user) {
		accepted.add(user);
	}

	public void removeUserFromAccepted(User user) {
		accepted.remove(user);
	}

	public void removeUserFromUnanswered(User user) {
		unanswered.remove(user);
	}

	public void addUserToUnanswered(User user) {
		unanswered.add(user);
	}

	public void addUserToRejected(User user) {
		rejected.add(user);
	}

	public void invite(User user) {
		invited.add(user);
	}

	public boolean hasUserBeenInvited(User user) {
		return invited.contains(user);
	}

	public boolean hasUserAccepted(User user) {
		return accepted.contains(user);
	}

	public boolean hasUserRejected(User user) {
		return rejected.contains(user);
	}

	public List<User> getInvitedUsers() {
		return invited;
	}

	public int eventStatus(User user) {
		int value;
		if (accepted.contains(user))
			value = 1;
		else if (rejected.contains(user))
			value = -1;
		else
			value = 0;
		return value;
	}

	/**
	 * Compares this event with other event based on their names and, in case of a
	 * tie, their host's names. 
	 * 1 if other event comes first 
	 * -1 if this event comes first 
	 * 0 if equal
	 * 
	 * @param other: other event to compare.
	 * @return number that represents order.
	 */
	public int compareTo(Event other) {
		int value;
		if (this.getName().equals(other.getName()))
			value = this.getHost().compareTo(other.getHost());
		else
			value = this.getName().compareTo(other.getName());
		return value;
	}
}
