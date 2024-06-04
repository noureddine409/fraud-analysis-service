package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Device;
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
        final Device device = event.getDevice();

        // Check for null values
        if (username == null || device == null || device.getFingerprint() == null) {
            log.warn("Invalid event data: username, device or device fingerprint is null");
            return FraudDetectionResult.builder()
                    .ruleName(RULE_NAME)
                    .eventId(eventId)
                    .isFraud(false)
                    .reason("Invalid event data")
                    .build();
        }

        boolean isUnknownDevice = jpaStreamer.stream(Event.class)
                .filter(e -> !e.getId().equals(eventId)) // exclude event with the same id
                .noneMatch(e -> {
                    Device eDevice = e.getDevice();
                    return eDevice != null && e.getUsername().equals(username) && eDevice.getFingerprint().equals(device.getFingerprint());
                });

        // Build the result with the appropriate reason
        return FraudDetectionResult.builder()
                .ruleName(RULE_NAME)
                .eventId(eventId)
                .isFraud(isUnknownDevice)
                .reason(isUnknownDevice ? "Unknown Device detected" : "Device is known")
                .build();
    }
}
