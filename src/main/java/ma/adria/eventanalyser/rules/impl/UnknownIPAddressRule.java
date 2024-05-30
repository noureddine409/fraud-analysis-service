package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.rules.FraudRule;
import org.springframework.stereotype.Component;

/**
 * A fraud detection rule that flags events with unknown IP addresses.
 */
@Component
@Slf4j
public class UnknownIPAddressRule implements FraudRule {

    /**
     * A fraud detection rule that flags events where a user connects from a new IP address.
     *
     * @param event       the event to evaluate
     * @param jpaStreamer the JPAStreamer instance used for querying
     * @return a {@link FraudDetectionResult} indicating if the event is fraudulent and the reason
     */
    @Override
    public FraudDetectionResult evaluate(Event event, JPAStreamer jpaStreamer) {

        // Extract the username and IP address from the event
        final String username = event.getUsername();
        final String ipAddress = event.getLocation().getIpAddress();
        // Check if the username has connected from the given IP address before
        boolean isUnknownIPAddress = jpaStreamer.stream(Event.class)
                .noneMatch(e -> e.getUsername().equals(username) && e.getLocation().getIpAddress().equals(ipAddress));

        // Build the result with the appropriate reason
        return FraudDetectionResult.builder()
                .isFraud(isUnknownIPAddress)
                .reason(isUnknownIPAddress ? "Unknown IP address detected" : "IP address is known")
                .build();
    }
}
