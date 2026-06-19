package company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import exceptions.*;
import java.time.LocalDateTime;

class CompanyClass implements Company {

	private SortedMap<String, User> users;
	private List<Event> allEvents;

	// constructor
	CompanyClass() {
		users = new TreeMap<String, User>();
		allEvents = new ArrayList<Event>();
	}

	public User getUser(String email) {
		return users.get(email);
	}

	/**
	 * Checks if a user with that email already exists.
	 * 
	 * @pre email != null.
	 * @return true if it exists, false otherwise.
	 */
	private boolean userExists(String email) {
		return users.containsKey(email);
	}

	public void register(String email, String type) throws AccountNameExistsException, UnknownAccountTypeException {
		if (userExists(email))
			throw new AccountNameExistsException();
		UserType convertedType = UserType.convert(type);
		if (convertedType == null)
			throw new UnknownAccountTypeException();
		User user = UserType.create(email, convertedType);
		users.put(email, user);
	}

	public Iterator<User> allUsersIterator() {
		return users.values().iterator();
	}

	public boolean hasUsers() {
		return !users.isEmpty();
	}

	public void addEvent(String email, String eventName, String priority, LocalDateTime date, String topics)
			throws AccountNameDoesNotExistException, EventPriorityUnknownException, CannotCreateEventsException,
			CannotCreateHighEventsException, SameEventNameException, SameTimeEventException {

		if (!userExists(email))
			throw new AccountNameDoesNotExistException();

		User user = getUser(email);

		if (!Priority.equalsMid(priority) && !Priority.equalsHigh(priority))
			throw new EventPriorityUnknownException();
		if (isGuest(email))
			throw new CannotCreateEventsException();
		if (Priority.equalsHigh(priority) && isStaffMember(email))
			throw new CannotCreateHighEventsException();
		if (getHostEvent(email, eventName) != null)
			throw new SameEventNameException();
		if (user.alreadyAcceptedEvent(date))
			throw new SameTimeEventException();

		((Worker) user).addEvent(email, eventName, priority, topics, date);
		Event event = user.getEventByName(email, eventName);
		user.removeSameTimeEvents(event, date);
		allEvents.add(event);
		event.addHost(user);
	}

	public Iterator<Event> allEventsIterator(String email) {
		return getUser(email).allEventsIterator();
	}

	public boolean hasEvents(String email) throws AccountNameDoesNotExistException {
		if (!userExists(email))
			throw new AccountNameDoesNotExistException();
		return getUser(email).hasEvents();
	}

	public void invite(String invitedEmail, String email, String eventName) throws AccountNameDoesNotExistException,
			HostDoesNotHaveEventException, AlreadyInvitedException, SameTimeEventException {

		if (!userExists(email))
			throw new AccountNameDoesNotExistException(email);
		if (!userExists(invitedEmail))
			throw new AccountNameDoesNotExistException(invitedEmail);
		User host = getUser(email);
		User invitedUser = getUser(invitedEmail);
		if (getHostEvent(email, eventName) == null)
			throw new HostDoesNotHaveEventException();

		Event hostEvent = host.getEventByName(email, eventName);
		LocalDateTime date = getEventDate(email, eventName);

		if (alreadyHasSameEvent(invitedEmail, email, date))
			throw new AlreadyInvitedException();
		if (hasOtherEvent(invitedEmail, email, eventName))
			throw new SameTimeEventException();

		if (invitedUser instanceof StaffClass)
			((Staff) invitedUser).invite(hostEvent);
		else if (invitedUser instanceof ManagerClass)
			((Manager) invitedUser).invite(hostEvent);
		else
			((Guest) invitedUser).invite(hostEvent);
	}

	public void removeOldHost(String invitedEmail, String email, String eventName) {
		User invitedUser = getUser(invitedEmail);
		LocalDateTime date = getEventDate(email, eventName);
		Event oldHostEvent = invitedUser.getEventByDate(invitedEmail, date);
		List<User> allUsers = oldHostEvent.getInvitedUsers();
		for (User user : allUsers) {
			user.removeEventFromUser(oldHostEvent);
		}
	}

	/**
	 * Get the host's event with a certain name.
	 * 
	 * @param email:     host's email.
	 * @param eventName: event's name.
	 * @pre email != null && eventName != null && event exists.
	 * @return event.
	 */
	private Event getHostEvent(String email, String eventName) {
		Event event = getUser(email).getEventByName(email, eventName);
		return event;
	}

	public boolean isHighPriority(String email, String eventName) {
		Event event = getUser(email).getEventByName(email, eventName);
		return Priority.equalsHigh(event.getPriority());
	}

	public boolean isStaffMember(String email) {
		return getUser(email) instanceof StaffClass;
	}

	/**
	 * Checks if the user with that email is a guest.
	 * 
	 * @param email: user's email.
	 * @pre email != null.
	 * @return true if the user is a guest, false otherwise.
	 */
	private boolean isGuest(String email) {
		return getUser(email) instanceof GuestClass;
	}

	public boolean isHost(String email, LocalDateTime date) {
		return getUser(email).getEventByDate(email, date) != null;
	}

	public boolean alreadyHasSameEvent(String invitedEmail, String email, LocalDateTime date) {
		return getUser(invitedEmail).alreadyHasSameEvent(email, date);
	}

	public boolean hasOtherEvent(String invitedEmail, String email, String eventName) {
		boolean has = false;
		User invitedUser = getUser(invitedEmail);
		LocalDateTime date = getEventDate(email, eventName);

		if (invitedUser.alreadyAcceptedEvent(date)) {
			String hostPriority = getHostEvent(email, eventName).getPriority();
			if (Priority.equalsMid(hostPriority))
				has = true;
			else {
				if (!(invitedUser instanceof StaffClass))
					has = true;
				else {
					if (Priority.equalsHigh(invitedUser.getEventPriority(date))) {
						has = true;
					}
				}
			}
		}

		return has;
	}

	public Iterator<Event> rejectedEventsList(String invitedEmail, String email, String eventName) {
		User invitedUser = getUser(invitedEmail);
		LocalDateTime date = getEventDate(email, eventName);
		Event newEvent = getUser(email).getEventByName(email, eventName);

		List<Event> sameTimeEvents = invitedUser.sameTimeEvents(date);
		sameTimeEvents.remove(newEvent);
		invitedUser.removeSameTimeEvents(newEvent, date);

		return sameTimeEvents.iterator();
	}

	public void answer(String invitedEmail, String email, String eventName, String answer)
			throws AccountNameDoesNotExistException, InvalidAnswerException, HostDoesNotHaveEventException,
			NotInvitedException, AlreadyRespondedException, SameTimeEventException {
		User invitedUser = getUser(invitedEmail);

		if (!userExists(email))
			throw new AccountNameDoesNotExistException(email);
		if (!userExists(invitedEmail))
			throw new AccountNameDoesNotExistException(invitedEmail);
		if (!Status.equalsAccept(answer) && !Status.equalsReject(answer))
			throw new InvalidAnswerException();
		if (getHostEvent(email, eventName) == null)
			throw new HostDoesNotHaveEventException();

		Event event = getHostEvent(email, eventName);
		LocalDateTime date = getEventDate(email, eventName);

		if (!event.hasUserBeenInvited(invitedUser))
			throw new NotInvitedException();
		if (event.hasUserAccepted(invitedUser) || event.hasUserRejected(invitedUser))
			throw new AlreadyRespondedException();
		if (invitedUser.alreadyAcceptedEvent(date))
			throw new SameTimeEventException();

		invitedUser.response(event, answer);
	}

	public LocalDateTime getEventDate(String email, String eventName) {
		return getUser(email).getEventDate(email, eventName);
	}
	
	public boolean hasDate(String email, String eventName) {
		Event event = getUser(email).getEventByName(email, eventName);
		return event != null;
	}

	public Iterator<User> getEventInvitedIterator(String email, String eventName)
			throws AccountNameDoesNotExistException, HostDoesNotHaveEventException {
		if (!userExists(email))
			throw new AccountNameDoesNotExistException();
		if (getHostEvent(email, eventName) == null)
			throw new HostDoesNotHaveEventException();
		User user = getUser(email);
		Event event = user.getEventByName(email, eventName);
		return event.getInvitedUsers().iterator();
	}

	public int getUserStatus(String email, String eventName, User user) {
		User host = getUser(email);
		Event event = host.getEventByName(email, eventName);
		return event.eventStatus(user);
	}

	/**
	 * Returns a map containing a list of events ordered by number of matching
	 * topics. Every single list is ordered by alphabetical order, first by the
	 * event description and then by the promoter email.
	 * 
	 * @param topics: array of requested topics.
	 * @return map.
	 */
	private Map<Integer, List<Event>> eventsByTopics(String[] topics) {
		// map ordered by number of requested topics
		Map<Integer, List<Event>> eventsByTopics = new HashMap<Integer, List<Event>>();

		for (Event event : allEvents) {
			String eventTopics = event.getTopics();
			String[] topicArray = eventTopics.split(SPACE);
			int counter = 0; // key of new map (number of shared topics)

			// compares current event's topics with requested topics
			for (String requestedTopic : topics) {
				for (String topic : topicArray) {
					if (requestedTopic.equals(topic))
						// for every shared topic, increments counter
						counter++;
				}
			}
			List<Event> list;
			if (eventsByTopics.containsKey(counter))
				list = eventsByTopics.get(counter);
			else {
				list = new ArrayList<Event>();
				eventsByTopics.put(counter, list);
			}
			list.add(event);
			Collections.sort(list);
		}
		return eventsByTopics;
	}

	public Iterator<Event> eventsByTopicsIterator(String[] topics) {

		// new list containing all events sharing the same topics
		List<Event> events = new ArrayList<>();
		int numTopics = topics.length;
		// iterate starting from the highest number of shared topics
		for (int i = numTopics; i > 0; i--) {
			if (eventsByTopics(topics).containsKey(i)) {
				List<Event> list = eventsByTopics(topics).get(i);
				// add each ordered list to a new list
				events.addAll(list);
			}
		}
		return events.iterator();
	}

	public boolean hasEventsForTopics(String[] topics) {
		boolean has = false;
		int numTopics = topics.length;
		for (int i = numTopics; i > 0; i--) {
			if (eventsByTopics(topics).containsKey(i))
				has = true;
		}
		return has;
	}
}
