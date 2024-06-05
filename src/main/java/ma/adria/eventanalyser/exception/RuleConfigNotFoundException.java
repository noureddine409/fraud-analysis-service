package ma.adria.eventanalyser.exception;

public class RuleConfigNotFoundException extends RuntimeException {

    public RuleConfigNotFoundException(String message, Long eventId) {
        super(String.format(message, eventId));
    }
}
