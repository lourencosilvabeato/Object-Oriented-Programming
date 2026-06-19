package company;

public enum Status {
	
	ACCEPT("accept"),
	REJECT("reject"),
	NO_ANSWER("no_answer");
	
	private final String answer;

	private Status(String answer) {
		this.answer = answer;
	}
	
	private String getAnswer() {
		return answer;
	}
	
	public static boolean equalsAccept(String response) {
		return response.equals(ACCEPT.getAnswer());
	}
	
	public static boolean equalsReject(String response) {
		return response.equals(REJECT.getAnswer());
	}
	
	public static String getUserStatus(int value) {
		String status = null;
		switch(value) {
		case 1 -> status = ACCEPT.getAnswer();
		case -1 -> status = REJECT.getAnswer();
		case 0 -> status = NO_ANSWER.getAnswer();
		}
		assert false;
		return status;
	}

}
