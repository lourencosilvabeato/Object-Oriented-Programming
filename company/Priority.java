package company;

public enum Priority {
	
	HIGH("high"),
	MID("mid");
	
	private final String priority;
	
	//constructor
	private Priority(String priority) {
		this.priority = priority;
	}
	
	/**
	 * Get priority type.
	 * @return priority type.
	 */
	public String getPriority() {
		return priority;
	}
	
	/**
	 * Checks if priority type is mid.
	 * @param priority: event's priority.
	 * @pre priority != null
	 * @return true if priority equals mid, false otherwise.
	 */
	public static boolean equalsMid(String priority) {
		return priority.equals(MID.getPriority());
	}
	
	/**
	 * Checks if priority type is high.
	 * @param priority: event's priority.
	 * @pre priority != null
	 * @return true if priority equals high, false otherwise.
	 */
	public static boolean equalsHigh(String priority) {
		return priority.equals(HIGH.getPriority());
	}

}
