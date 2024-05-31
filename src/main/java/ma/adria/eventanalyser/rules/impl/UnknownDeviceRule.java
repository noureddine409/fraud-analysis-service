package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.rules.FraudRule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class UnknownDeviceRule implements FraudRule {

    private static final String RULE_NAME = "Unknown device";


    private final ModelMapper modelMapper;

    private final JPAStreamer jpaStreamer;

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        // map from dto to event entity
        Event event = modelMapper.map(eventDto, Event.class);

        final Long eventId = event.getId();

        final String username = event.getUsername();
        final String fingerprint = event.getDevice().getFingerprint();

        boolean isUnknownDevice = jpaStreamer.stream(Event.class)
                .filter(e -> !e.getId().equals(eventId)) // exclude event with the same id
                .noneMatch(e -> e.getUsername().equals(username) && e.getDevice().getFingerprint().equals(fingerprint));

        // Build the result with the appropriate reason
        return FraudDetectionResult.builder()
                .ruleName(RULE_NAME)
                .eventId(eventId)
                .isFraud(isUnknownDevice)
                .reason(isUnknownDevice ? "Unknown Device detected" : "Device is known")
                .build();
    }
}
