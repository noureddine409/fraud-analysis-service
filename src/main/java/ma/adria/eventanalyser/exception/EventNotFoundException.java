package ma.adria.eventanalyser.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message, Long eventId) {
        super(String.format(message, eventId));
    }
}
