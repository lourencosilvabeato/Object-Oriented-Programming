package company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

abstract class WorkerClass extends UserClass implements Worker {

	//constructor
	WorkerClass(String email) {
		super(email);
	}

	public void addEvent(String host, String eventName, String priority, String topics, LocalDateTime date) {
		Event event = new EventClass(host, eventName, date, priority, topics);
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

		accepted.add(event);
	}
}
