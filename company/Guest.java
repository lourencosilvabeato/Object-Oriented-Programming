package company;

public interface Guest {

	/**
	 * The guest is invited to the event. Therefore, the
	 * event's list is updated and the guest's event
	 * list as well.
	 * @param event: event the guest is invited to.
	 * @pre event != null
	 */
	void invite(Event event);

}
