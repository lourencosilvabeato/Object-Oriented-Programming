package company;

import java.time.LocalDateTime;
import java.util.Iterator;
import exceptions.*;

public class CalendarAppClass implements CalendarApp {

	private Company company;

	// constructor
	public CalendarAppClass() {
		company = new CompanyClass();
	}

	public void register(String email, String type) throws AccountNameExistsException, UnknownAccountTypeException {
		company.register(email, type);
	}

	public Iterator<User> allUsersIterator() {
		return company.allUsersIterator();
	}

	public boolean hasUsers() {
		return company.hasUsers();
	}

	public void addEvent(String email, String eventName, String priority, java.time.LocalDateTime date, String topics)
			throws AccountNameDoesNotExistException, EventPriorityUnknownException, CannotCreateEventsException,
			CannotCreateHighEventsException, SameEventNameException, SameTimeEventException {
		company.addEvent(email, eventName, priority, date, topics);
	}

	public Iterator<Event> allEventsIterator(String email) {
		return company.allEventsIterator(email);
	}

	public boolean hasEvents(String email) throws AccountNameDoesNotExistException {
		return company.hasEvents(email);
	}

	public void invite(String invitedEmail, String email, String eventName) throws AccountNameDoesNotExistException,
			HostDoesNotHaveEventException, AlreadyInvitedException, SameTimeEventException {
		company.invite(invitedEmail, email, eventName);
	}

	public void removeOldHost(String invitedEmail, String email, String eventName) {
		company.removeOldHost(invitedEmail, email, eventName);
	}

	public String getHostEventName(String invitedEmail, String email, String eventName) {
		LocalDateTime date = company.getEventDate(email, eventName);
		return company.getUser(invitedEmail).getEventByDate(invitedEmail, date).getName();
	}

	public boolean isHighPriority(String email, String eventName) {
		return company.isHighPriority(email, eventName);
	}

	public boolean isStaffMember(String invitedEmail) {
		return company.isStaffMember(invitedEmail);
	}

	public boolean hasOtherEvent(String invitedEmail, String email, String eventName) {
		return company.hasOtherEvent(invitedEmail, email, eventName);
	}

	public boolean isHost(String invitedEmail, String email, String eventName) {
		LocalDateTime date = company.getEventDate(email, eventName);
		return company.isHost(invitedEmail, date);
	}

	public boolean alreadyHasSameEvent(String invitedEmail, String email, String eventName) {
		LocalDateTime date = company.getEventDate(email, eventName);
		return company.alreadyHasSameEvent(invitedEmail, email, date);
	}

	public Iterator<Event> rejectedEventsList(String invitedEmail, String email, String eventName) {
		return company.rejectedEventsList(invitedEmail, email, eventName);
	}

	public void answer(String invitedEmail, String email, String eventName, String answer)
			throws AccountNameDoesNotExistException, InvalidAnswerException, HostDoesNotHaveEventException,
			NotInvitedException, AlreadyRespondedException, SameTimeEventException {
		company.answer(invitedEmail, email, eventName, answer);
	}

	public LocalDateTime getEventDate(String email, String eventName) {
		return company.getEventDate(email, eventName);
	}

	public boolean hasDate(String email, String eventName) {
		return company.hasDate(email, eventName);
	}

	public Iterator<User> eventsByTopicsIterator(String email, String eventName)
			throws AccountNameDoesNotExistException, HostDoesNotHaveEventException {
		return company.getEventInvitedIterator(email, eventName);
	}

	public String getUserStatus(String email, String eventName, User user) {
		int value = company.getUserStatus(email, eventName, user);
		return Status.getUserStatus(value);
	}

	public Iterator<Event> eventsByTopicsIterator(String[] topics) {
		return company.eventsByTopicsIterator(topics);
	}

	public boolean hasEventsForTopics(String[] topics) {
		return company.hasEventsForTopics(topics);
	}
}
