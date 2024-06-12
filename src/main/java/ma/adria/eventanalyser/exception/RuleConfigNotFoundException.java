package ma.adria.eventanalyser.exception;

public class RuleConfigNotFoundException extends RuntimeException {

    public RuleConfigNotFoundException(String message, String code) {
        super(String.format(message, code));
    }
}
