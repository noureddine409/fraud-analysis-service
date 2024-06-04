package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.Location;
import ma.adria.eventanalyser.rules.FraudRule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A fraud detection rule that flags events with unknown IP addresses.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UnknownIPAddressRule implements FraudRule {

    private static final String RULE_NAME = "Unknown IP Address";

    private final ModelMapper modelMapper;
    private final JPAStreamer jpaStreamer;

    /**
     * A fraud detection rule that flags events where a user connects from a new IP address.
     *
     * @param eventDto the event to evaluate
     * @return a {@link FraudDetectionResult} indicating if the event is fraudulent and the reason
     */
    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {

        // map from dto to event entity
        final Event event = modelMapper.map(eventDto, Event.class);

        // Extract the username and IP address from the event
        final String username = event.getUsername();
        final Location location = event.getLocation();
        final Long eventId = event.getId();

        if (location == null || username == null || location.getIpAddress() == null) {
            log.warn("Invalid event data: username, location is null");
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason("Invalid event data")
                    .build();
        }

        // Check if the username has connected from the given IP address before
        boolean isUnknownIPAddress = jpaStreamer.stream(Event.class)
                .filter(e -> !e.getId().equals(eventId)) // Exclude event with the same ID
                .noneMatch(e -> e.getUsername().equals(username) && e.getLocation().getIpAddress().equals(location.getIpAddress()));

        // Build the result with the appropriate reason
        return FraudDetectionResult.builder()
                .ruleName(RULE_NAME)
                .eventId(eventId)
                .isFraud(isUnknownIPAddress)
                .reason(isUnknownIPAddress ? "Unknown IP address detected" : "IP address is known")
                .build();
    }
}
