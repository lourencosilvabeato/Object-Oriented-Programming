package company;

public enum UserType {
	STAFF("staff"),
	MANAGER("manager"),
	GUEST("guest");

	private final String type;

	//constructor
	private UserType(String type) {
		this.type = type;
	}

	/**
	 * Get user type.
	 * @return user type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Converts string into UserType.
	 * @param userType: user's type.
	 * @pre userType equals staff, manager or guest.
	 * @return the user's type as UserType.
	 */
	public static UserType convert(String userType) {
		UserType getType = null;
		if(userType.equals(STAFF.getType()))
			getType = STAFF;
		if(userType.equals(GUEST.getType()))
			getType = GUEST;
		if(userType.equals(MANAGER.getType()))
			getType = MANAGER;
		return getType;
	}

	/**
	 * Creates a new user.
	 * @param email: user's email.
	 * @param type: user's type.
	 * @pre userType equals staff, manager or guest && email != null.
	 * @return new user.
	 */
	public static User create(String email, UserType type) {
		User user = null;
		switch (type) {
		case STAFF	 -> user = new StaffClass(email);
		case GUEST 	 -> user = new GuestClass(email);
		case MANAGER -> user = new ManagerClass(email);
		}
		return user;
	}

	/**
	 * Get the users's type.
	 * @param user: the user.
	 * @pre user != null.
	 * @return user's type.
	 */
	public static String getUserType(User user) {
		if(user instanceof StaffClass)
			return STAFF.getType();
		if(user instanceof GuestClass)
			return GUEST.getType();
		else			
			return MANAGER.getType();
	}
}

