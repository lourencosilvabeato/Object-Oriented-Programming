package company;

class StaffClass extends WorkerClass implements Staff {

	//constructor
	StaffClass(String email) {
		super(email);
	}

	public void invite(Event event) {
		if(Priority.equalsHigh(event.getPriority())) {
			accepted.add(event);
			event.addUserToAccepted(this);
		}
		else {
			unanswered.add(event);
			event.addUserToUnanswered(this);
		}
		super.invite(event);
		event.invite(this);
	}
}
