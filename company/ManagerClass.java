package company;

class ManagerClass extends WorkerClass implements Manager {

	//constructor
	ManagerClass(String email) {
		super(email);
	}

	public void invite(Event event) {
		unanswered.add(event);
		event.addUserToUnanswered(this);
		super.invite(event);
		event.invite(this);
	}
}
