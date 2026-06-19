package exceptions;

@SuppressWarnings("serial")
public class AccountNameDoesNotExistException extends Exception {
	
	public AccountNameDoesNotExistException() {
		super();
	}
	
	public AccountNameDoesNotExistException(String email) {
		super(email);
	}
}
