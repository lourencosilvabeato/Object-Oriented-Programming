# Calendar Event Management System

## Overview

This project implements a calendar and event management system in Java, developed as a university assignment for an Object-Oriented Programming course. Users of different roles can register accounts, host events, invite one another, and respond to scheduling requests. The system enforces role-based permissions, priority-driven conflict resolution, and a full invitation lifecycle through a structured class hierarchy.

## Core Technologies

The project is written entirely in Java and demonstrates the four pillars of object-oriented design: abstraction, encapsulation, inheritance, and polymorphism. Contracts are defined through interfaces, shared behavior is centralized in abstract classes, and role-specific logic is implemented in concrete subclasses. Thirteen custom exception classes handle distinct error scenarios, keeping validation concerns clearly separated from business logic.

## Architecture & Business Logic

The codebase is organized into two packages. The `company` package contains the full domain model — interfaces (`User`, `Event`, `Company`, `CalendarApp`), abstract base classes (`UserClass`, `WorkerClass`), concrete user types (`StaffClass`, `ManagerClass`, `GuestClass`), and the event and application classes. The `exceptions` package isolates all thirteen error types. A `Main.java` entry point implements a command-line interpreter that processes nine commands — `register`, `accounts`, `create`, `events`, `invite`, `response`, `event`, `topics`, and `help` — routing each to the `CalendarAppClass` facade.

Three roles drive the system's behavior: Managers can host events of any priority and handle invitations manually; Staff can only host mid-priority events and automatically accept high-priority invitations, causing any conflicting lower-priority commitments to be rejected in the process; Guests cannot host events and may only receive invitations. The scheduling engine prevents time conflicts by tracking each user's accepted events and enforcing slot exclusivity at both invitation and response time.

## How to Run

Compile all Java source files from the project root using `javac` and run the entry point with `java Main`. The program opens an interactive command-line session — type `help` to list all available commands. Each command reads its arguments from standard input and outputs results or descriptive error messages accordingly.

```bash
javac -d out Main.java company/*.java exceptions/*.java
java -cp out Main
```

## Demo

The session below registers three accounts, creates a high-priority event, and walks through the full invitation and response lifecycle.

```
$ register alice@company.com manager
alice@company.com was registered.

$ register bob@company.com staff
bob@company.com was registered.

$ register carol@company.com guest
carol@company.com was registered.

$ accounts
All accounts:
alice@company.com [manager]
bob@company.com [staff]
carol@company.com [guest]

$ create alice@company.com
> Strategy Meeting
> high
> 2026 8 15 14
> planning strategy Q3
Strategy Meeting is scheduled.

$ invite bob@company.com
> alice@company.com Strategy Meeting
bob@company.com accepted the invitation.         ← staff auto-accepts high-priority events

$ invite carol@company.com
> alice@company.com Strategy Meeting
carol@company.com was invited.

$ response carol@company.com
> alice@company.com Strategy Meeting
> accept
Account carol@company.com has replied accept to the invitation.

$ event alice@company.com Strategy Meeting
Strategy Meeting occurs on 15-08-2026 14h:
alice@company.com [accept]
bob@company.com [accept]
carol@company.com [accept]

$ events alice@company.com
Account alice@company.com events:
Strategy Meeting status [invited 3] [accepted 3] [rejected 0] [unanswered 0]

$ topics planning
Events on topics planning:
Strategy Meeting promoted by alice@company.com on planning strategy Q3
```

The next example shows the role-based constraint enforced at event creation — a staff account attempting to host a high-priority event is rejected by the system.

```
$ create bob@company.com
> All Hands
> high
> 2026 9 10 10
> updates all-hands
Account bob@company.com cannot create high priority events.   ← staff role restriction
```
