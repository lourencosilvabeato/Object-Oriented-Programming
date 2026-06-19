package company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

abstract class UserClass implements User {

	protected Map<String, List<Event>> eventsByName;
	protected Map<LocalDateTime, List<Event>> eventsByDate;
	protected List<Event> allEvents;
	protected List<Event> accepted;
	protected List<Event> unanswered;
	private List<Event> rejected;
	private String email;

	//constructor
	UserClass(String email) {
		eventsByName = new HashMap<String, List<Event>>();
		eventsByDate = new HashMap<LocalDateTime, List<Event>>();
		allEvents = new ArrayList<Event>();
		rejected = new ArrayList<Event>();
		accepted = new ArrayList<Event>();
		unanswered = new ArrayList<Event>();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public Iterator<Event> allEventsIterator() {
		return allEvents.iterator();
	}
	
	public boolean hasEvents() {
		return !allEvents.isEmpty();
	}

	public Event getEventByName(String email, String eventName) {
		List<Event> list = eventsByName.get(eventName);
		Event event = null;
		if (list != null) {
			int i = 0;
			while (event == null && i < list.size()) {
				if (list.get(i).getHost().equals(email))
					event = list.get(i);
				else
					i++;
			}
		}
		return event;
	}

	public Event getEventByDate(String email, LocalDateTime date) {
		List<Event> list = eventsByDate.get(date);
		Event event = null;
		if (list != null) {
			int i = 0;
			while (event == null && i < list.size()) {
				if (list.get(i).getHost().equals(email))
					event = list.get(i);
				else
					i++;
			}
		}
		return event;
	}

	public String getEventPriority(LocalDateTime date) {
		Event event = null;
		int i = 0;
		while (i < accepted.size() && event == null) {
			if (accepted.get(i).getDate().equals(date))
				event = accepted.get(i);
			else
				i++;
		}
		return event.getPriority();
	}

	public LocalDateTime getEventDate(String email, String eventName) {
		Event event = getEventByName(email, eventName);
		LocalDateTime date = event.getDate();
		return date;
	}

	public boolean alreadyAcceptedEvent(Event event) {
		return accepted.contains(event);
	}

	public boolean alreadyAcceptedEvent(LocalDateTime date) {
		boolean found = false;
		int i = 0;
		while (i < accepted.size() && !found) {
			if (accepted.get(i).getDate().equals(date))
				found = true;
			else
				i++;
		}
		return found;
	}
	
	public boolean alreadyHasSameEvent(String email, LocalDateTime date) {
		return getEventByDate(email, date) != null;
	}

	public boolean unansweredEvent(Event event) {
		return unanswered.contains(event);
	}

	public boolean unansweredEvent(String email, LocalDateTime date) {
		Event event = getEventByDate(email, date);
		return event != null && unanswered.contains(event);
	}

	public void removeEventFromUser(Event event) {
		allEvents.remove(event);
		accepted.remove(event);
		eventsByName.remove(event.getName());
		eventsByDate.remove(event.getDate());
		if (accepted.contains(event))
			accepted.remove(event);
		if (unanswered.contains(event))
			unanswered.remove(event);
		if (rejected.contains(event))
			rejected.remove(event);

	}

	public void invite(Event event) {
		String eventName = event.getName();
		LocalDateTime date = event.getDate();
		allEvents.add(event);

		List<Event> listByName;
		if (eventsByName.containsKey(eventName))
			listByName = eventsByName.get(eventName);
		else {
			listByName = new ArrayList<Event>();
			eventsByName.put(eventName, listByName);
		}
		listByName.add(event);

		List<Event> listByDate;
		if (eventsByDate.containsKey(date))
			listByDate = eventsByDate.get(date);
		else {
			listByDate = new ArrayList<Event>();
			eventsByDate.put(date, listByDate);
		}
		listByDate.add(event);
	}
	
	public void response(Event event, String answer) {
		if (Status.equalsAccept(answer)) {
			unanswered.remove(event);
			accepted.add(event);
			event.addUserToAccepted(this);
			event.removeUserFromUnanswered(this);
		} else {
			unanswered.remove(event);
			rejected.add(event);
			event.addUserToRejected(this);
			event.removeUserFromUnanswered(this);
		}
	}

	public List<Event> sameTimeEvents(LocalDateTime date) {
		List<Event> sameTimeEvents = new ArrayList<Event>();
		for (Event event : allEvents) {
			if (unanswered.contains(event) || accepted.contains(event)) {
				if (event.getDate().equals(date)) {
					sameTimeEvents.add(event);
				}
			}
		}
		return sameTimeEvents;
	}
	
	public void removeSameTimeEvents(Event newEvent, LocalDateTime date) {
		List<Event> list = sameTimeEvents(date);
		list.remove(newEvent);
		
		for (Event sameTimeEvent : list) {
			if (unansweredEvent(sameTimeEvent)) {
				unanswered.remove(sameTimeEvent);
				sameTimeEvent.removeUserFromUnanswered(this);
			} else if (alreadyAcceptedEvent(sameTimeEvent)) {
				accepted.remove(sameTimeEvent);
				sameTimeEvent.removeUserFromAccepted(this);
			}
			rejected.add(sameTimeEvent);
			sameTimeEvent.addUserToRejected(this);
		}
	}
}
