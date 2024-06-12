package ma.adria.eventanalyser.exception;

public class RuleConfigNotFoundException extends RuntimeException {

    public RuleConfigNotFoundException(String message, String code) {
        super(String.format(message, code));
    }

    public RuleConfigNotFoundException(String message, String code, Throwable cause) {
        super(String.format("%s: %s", message, code), cause);
    }
}
