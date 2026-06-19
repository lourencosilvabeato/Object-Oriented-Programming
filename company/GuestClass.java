package company;

class GuestClass extends UserClass implements Guest {

	//constructor
	GuestClass(String email) {
		super(email);
	}

	public void invite(Event event) {
		unanswered.add(event);
		event.addUserToUnanswered(this);
		super.invite(event);
		event.invite(this);
	}
}
