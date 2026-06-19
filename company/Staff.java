package company;

public interface Staff {

	/**
	 * The staff is invited to the event. If the event's priority
	 * is high, the staff is forced to accepted. Otherwise, it is
	 * a normal invitation.
	 * The event's list is updated and the staff's event
	 * list as well.
	 * @param event: event the guest is invited to.
	 * @pre event != null
	 */
	void invite(Event event);
}
