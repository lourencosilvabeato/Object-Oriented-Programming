import java.util.Iterator;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import company.*;
import exceptions.*;

public class Main {

	//program's feedback
	private static final String ALL_CMDS = "Available commands:\n";
	private static final String REGISTER = "register - registers a new account\n";
	private static final String ACCOUNTS = "accounts - lists all registered accounts\n";
	private static final String CREATE = "create - creates a new event\n";
	private static final String EVENTS = "events - lists all events of an account\n";
	private static final String INVITE = "invite - invites an user to an event\n";
	private static final String RESPONSE = "response - response to an invitation\n";
	private static final String EVENT = "event - shows detailed information of an event\n";
	private static final String TOPICS = "topics - shows all events that cover a list of topics\n";
	private static final String HELP = "help - shows the available commands\n";
	private static final String EXIT = "exit - terminates the execution of the program\n";
	private static final String BYE = "Bye!\n";
	private static final String INVALID_COMMAND_MSG = "Unknown command %s. Type help to see available commands.\n";
	private static final String ALL_ACCOUNTS = "All accounts:\n";
	private static final String USER = "%s [%s]\n";
	private static final String EVENT_SCHEDULED = "%s is scheduled.\n";
	private static final String WAS_REGISTERED = "%s was registered.\n";
	private static final String NAME_EXISTS = "Account %s already exists.\n";
	private static final String UNKNOWN_ACCOUNT_TYPE = "Unknown account type.\n";
	private static final String NO_ACCOUNT = "No account registered.\n";
	private static final String UNKNOWN_PRIORITY_TYPE = "Unknown priority type.\n";
	private static final String CANNOT_CREATE_EVENTS = "Guest account %s cannot create events.\n";
	private static final String INEXISTENT_NAME = "Account %s does not exist.\n";
	private static final String CANNOT_CREATE_HIGH_EVENTS = "Account %s cannot create high priority events.\n";
	private static final String EVENT_EXISTS = "%s already exists in account %s.\n";
	private static final String PROMOTER_BUSY = "Account %s is busy.\n";
	private static final String NO_EVENTS = "Account %s has no events.\n";
	private static final String SHOW_EVENTS = "Account %s events:\n";
	private static final String EVENT_INFO = "%s status [invited %d] [accepted %d] [rejected %d] [unanswered %d]\n";
	private static final String REJECTED_EVENT = "%s promoted by %s was rejected.\n";
	private static final String STAFF_ACCEPTED = "%s accepted the invitation.\n";
	private static final String EVENT_REMOVED = "%s promoted by %s was removed.\n";
	private static final String GUEST_INVITED = "%s was invited.\n";
	private static final String INEXISTENT_EVENT = "%s does not exist in account %s.\n";
	private static final String ALREADY_INVITED = "Account %s was already invited.\n";
	private static final String ATTENDING_OTHER = "Account %s already attending another event.\n";
	private static final String ANSWER = "Account %s has replied %s to the invitation.\n";
	private static final String UNKNOWN_RESPONSE = "Unknown event response.\n";
	private static final String NOT_INVITED = "Account %s is not on the invitation list.\n";
	private static final String ALREADY_RESPONDED = "Account %s has already responded.\n";
	private static final String EVENT_DETAILS = "%s occurs on %s:\n";
	private static final String WRITE_DATE_FORMAT = "dd-MM-yyyy HH'h'";
	private static final String EVENT_LIST = "%s [%s]\n";
	private static final String NO_EVENTS_TOPICS = "No events on those topics.\n";
	private static final String TOPICS_DETAILS = "Events on topics %s:\n";
	private static final String TOPICS_LIST = "%s promoted by %s on %s\n";
		
	private enum Command {
		/**
		 * Enum that defines the user's commands
		 */
		REGISTER, ACCOUNTS, CREATE, EVENTS, 
		INVITE, RESPONSE, EVENT, TOPICS, HELP, EXIT, INVALID
	};

	/**
	 * Main method. Invokes the command interpreter.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		commands();
	}
	

	/**
	 * Reads the input command and returns the corresponding enum value.
	 * @param input: command which the scanner will read.
	 * @return value of the enum which represent the command.
	 */
	private static Command getCommand(String input) {
		try {
			return Command.valueOf(input.toUpperCase());
		} catch (IllegalArgumentException e) {
			return Command.INVALID;
		}
	}

	/**
	 * Command interpreter.
	 */
	private static void commands() {
		CalendarApp app = new CalendarAppClass();
		Scanner in = new Scanner(System.in);
		Command command;
		String input;
		do {
			input = in.next().toUpperCase();
			command = getCommand(input);
			switch (command) {
			case REGISTER -> register(in, app);
			case ACCOUNTS -> accounts(app);
			case CREATE -> create(in, app);
			case EVENTS -> events(in, app);
			case INVITE -> invite(in, app);
			case RESPONSE -> response(in, app);
			case EVENT -> event(in, app);
			case TOPICS -> topics(in, app);
			case HELP -> help();
			case EXIT -> System.out.print(BYE);
			case INVALID -> System.out.printf(INVALID_COMMAND_MSG, input);
			}
		} while (!command.equals(Command.EXIT));
		in.close();
	}

	/**
	 * Registers a new user into the system.
	 */
	private static void register(Scanner in, CalendarApp app) {
		String email = in.next();
		String type = in.next();

		try {
			app.register(email, type);
			System.out.printf(WAS_REGISTERED, email);
		} catch (AccountNameExistsException e) {
			System.out.printf(NAME_EXISTS, email);
		} catch (UnknownAccountTypeException e) {
			System.out.print(UNKNOWN_ACCOUNT_TYPE);
		}
	}

	/**
	 * Lists all the registered users and their respective type.
	 */
	private static void accounts(CalendarApp app) {
		if (!app.hasUsers())
			System.out.print(NO_ACCOUNT);
		else {
			System.out.print(ALL_ACCOUNTS);
			Iterator<User> it = app.allUsersIterator();
			while (it.hasNext()) {
				User user = it.next();
				System.out.printf(USER, user.getEmail(), UserType.getUserType(user));
			}
		}
	}

	/**
	 * Creates a new event.
	 */
	private static void create(Scanner in, CalendarApp app) {
		String email = in.nextLine().trim();
		String eventName = in.nextLine().trim();
		String priority = in.next().trim();
		int year = in.nextInt();
		int month = in.nextInt();
		int day = in.nextInt();
		int time = in.nextInt();
		in.nextLine();
		String topics = in.nextLine().trim();
		LocalDateTime date = LocalDateTime.of(year, month, day, time, 0);

		try {
			app.addEvent(email, eventName, priority, date, topics);
			System.out.printf(EVENT_SCHEDULED, eventName);
		} catch (AccountNameDoesNotExistException e) {
			System.out.printf(INEXISTENT_NAME, email);

		} catch (EventPriorityUnknownException e) {
			System.out.print(UNKNOWN_PRIORITY_TYPE);

		} catch (CannotCreateEventsException e) {
			System.out.printf(CANNOT_CREATE_EVENTS, email);
		} catch (CannotCreateHighEventsException e) {
			System.out.printf(CANNOT_CREATE_HIGH_EVENTS, email);
		} catch (SameEventNameException e) {
			System.out.printf(EVENT_EXISTS, eventName, email);
		} catch (SameTimeEventException e) {
			System.out.printf(PROMOTER_BUSY, email);
		}
	}

	/**
	 * Lists all the events of a certain user.
	 */
	private static void events(Scanner in, CalendarApp app) {
		String email = in.nextLine().trim();

		try {
			if (!app.hasEvents(email))
				System.out.printf(NO_EVENTS, email);
			else {
				System.out.printf(SHOW_EVENTS, email);
				Iterator<Event> it = app.allEventsIterator(email);
				while (it.hasNext()) {
					Event event = it.next();
					System.out.printf(EVENT_INFO, event.getName(), event.getInvitedSize(), event.getAcceptedSize(),
							event.getRejectedSize(), event.getUnansweredSize());
				}
			}
		} catch (AccountNameDoesNotExistException e) {
			System.out.printf(INEXISTENT_NAME, email);
		}
	}

	/**
	 * Invites a user to a certain event.
	 */
	private static void invite(Scanner in, CalendarApp app) {
		String invitedEmail = in.nextLine().trim();
		String email = in.next().trim();
		String eventName = in.nextLine().trim();

		try {
			app.invite(invitedEmail, email, eventName);
			if (app.isHighPriority(email, eventName) && app.isStaffMember(invitedEmail)) {
				System.out.printf(STAFF_ACCEPTED, invitedEmail);
				if (app.isHost(invitedEmail, email, eventName)) {
					System.out.printf(EVENT_REMOVED, app.getHostEventName(invitedEmail, email, eventName),
							invitedEmail);
					app.removeOldHost(invitedEmail, email, eventName);
				} else if (app.alreadyHasSameEvent(invitedEmail, email, eventName)) {
					Iterator<Event> it = app.rejectedEventsList(invitedEmail, email, eventName);
					while (it.hasNext()) {
						Event event = it.next();
						System.out.printf(REJECTED_EVENT, event.getName(), event.getHost());
					}
				}
			} else
				System.out.printf(GUEST_INVITED, invitedEmail);
		} catch (AccountNameDoesNotExistException e) {
			System.out.printf(INEXISTENT_NAME, e.getMessage());
		} catch (HostDoesNotHaveEventException e) {
			System.out.printf(INEXISTENT_EVENT, eventName, email);
		} catch (AlreadyInvitedException e) {
			System.out.printf(ALREADY_INVITED, invitedEmail);
		} catch (SameTimeEventException e) {
			System.out.printf(ATTENDING_OTHER, invitedEmail);
		}
	}

	/**
	 * Responds to a certain invitation.
	 */
	private static void response(Scanner in, CalendarApp app) {
		String invitedEmail = in.nextLine().trim();
		String email = in.next().trim();
		String eventName = in.nextLine().trim();
		String answer = in.nextLine().trim();

		try {
			app.answer(invitedEmail, email, eventName, answer);
			System.out.printf(ANSWER, invitedEmail, answer);
			if (Status.equalsAccept(answer)) {
				Iterator<Event> it = app.rejectedEventsList(invitedEmail, email, eventName);
				while (it.hasNext()) {
					Event event = it.next();
					System.out.printf(REJECTED_EVENT, event.getName(), event.getHost());
				}
			}
		} catch (AccountNameDoesNotExistException e) {
			System.out.printf(INEXISTENT_NAME, e.getMessage());
		} catch (InvalidAnswerException e) {
			System.out.print(UNKNOWN_RESPONSE);
		} catch (HostDoesNotHaveEventException e) {
			System.out.printf(INEXISTENT_EVENT, eventName, email);
		} catch (NotInvitedException e) {
			System.out.printf(NOT_INVITED, invitedEmail);
		} catch (AlreadyRespondedException e) {
			System.out.printf(ALREADY_RESPONDED, invitedEmail);
		} catch (SameTimeEventException e) {
			System.out.printf(ATTENDING_OTHER, invitedEmail);
		}

	}

	/**
	 * Lists all the users and their respective status related to a certain event.
	 */
	private static void event(Scanner in, CalendarApp app) {
		String email = in.next().trim();
		String eventName = in.nextLine().trim();

		try {
			if (app.hasDate(email, eventName)) {
				LocalDateTime date = app.getEventDate(email, eventName);
				DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(WRITE_DATE_FORMAT);
				String dateAsString = formatterDate.format(date);
				System.out.printf(EVENT_DETAILS, eventName, dateAsString);
			}

			Iterator<User> it = app.eventsByTopicsIterator(email, eventName);
			while (it.hasNext()) {
				User user = it.next();
				String status = app.getUserStatus(email, eventName, user);
				System.out.printf(EVENT_LIST, user.getEmail(), status);
			}
		} catch (AccountNameDoesNotExistException e) {
			System.out.printf(INEXISTENT_NAME, email);
		} catch (HostDoesNotHaveEventException e) {
			System.out.printf(INEXISTENT_EVENT, eventName, email);
		}
	}

	/**
	 *Lists all the events containing at least one of the requested topics.
	 */
	private static void topics(Scanner in, CalendarApp app) {
		String allTopics = in.nextLine().trim();
		String[] topics = allTopics.split(" ");

		if (!app.hasEventsForTopics(topics))
			System.out.printf(NO_EVENTS_TOPICS);
		else {
			System.out.printf(TOPICS_DETAILS, allTopics);
			Iterator<Event> it = app.eventsByTopicsIterator(topics);
			while (it.hasNext()) {
				Event event = it.next();
				System.out.printf(TOPICS_LIST, event.getName(), event.getHost(), event.getTopics());
			}
		}
	}

	/**
	 *Lists all the commands available.
	 */
	private static void help() {
		System.out.print(ALL_CMDS);
		System.out.print(REGISTER + ACCOUNTS + CREATE + EVENTS + INVITE + RESPONSE + EVENT + TOPICS + HELP + EXIT);
	}
}
