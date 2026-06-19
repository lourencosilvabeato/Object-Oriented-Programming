package company;

public interface Manager {

	/**
	 * The manager is invited to the event. Therefore, the
	 * event's list is updated and the manager's event
	 * list as well.
	 * @param event: event the manager is invited to.
	 * @pre event != null
	 */
	void invite(Event event);
}
