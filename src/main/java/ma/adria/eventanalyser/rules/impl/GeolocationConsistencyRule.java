package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.Location;
import ma.adria.eventanalyser.rules.FraudRule;
import ma.adria.eventanalyser.utils.GeoLocationUtils;
import ma.adria.eventanalyser.utils.RuleConfigUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeolocationConsistencyRule implements FraudRule {

    private static final String RULE_NAME = "Geolocation Inconsistency";

    private final ModelMapper modelMapper;
    private final JPAStreamer jpaStreamer;

    // Maximum allowable speed in km/h
    private static final double MAX_ALLOWED_SPEED_KMH = 100.0;

    @Override
    public FraudDetectionResult evaluate(EventDto eventDto) {
        if (eventDto == null) {
            log.warn("Invalid event data: eventDto is null");
            return RuleConfigUtils.buildResult(null, RULE_NAME, false, "eventDto is null");
        }

        Event event = modelMapper.map(eventDto, Event.class);
        Long eventId = event.getId();
        String username = event.getUsername();
        Location location = event.getLocation();

        if (username == null || location == null || location.getGeolocation() == null) {
            log.warn("Invalid event data: username or geolocation data is null");
            return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, "Invalid event data");
        }

        log.debug("Evaluating geolocation consistency for event with ID: {}", eventId);

        // Fetch the most recent event with valid location for the user
        Optional<Event> latestEventWithLocationOpt = jpaStreamer.stream(Event.class)
                .filter(e -> e.getUsername().equals(username) && e.getLocation() != null && e.getLocation().getGeolocation() != null)
                .min((e1, e2) -> e2.getTimestamp().compareTo(e1.getTimestamp()));

        if (latestEventWithLocationOpt.isPresent()) {
            Event latestEventWithLocation = latestEventWithLocationOpt.get();
            double distance = GeoLocationUtils.calculateDistance(
                    location.getGeolocation().getLatitude(),
                    location.getGeolocation().getLongitude(),
                    latestEventWithLocation.getLocation().getGeolocation().getLatitude(),
                    latestEventWithLocation.getLocation().getGeolocation().getLongitude()
            );
            Duration timeDifference = Duration.between(latestEventWithLocation.getTimestamp(), event.getTimestamp());

            // Calculate speed in km/h
            double speedKmh = GeoLocationUtils.calculateSpeed(distance, timeDifference);

            // Check against maximum allowed speed
            if (speedKmh > MAX_ALLOWED_SPEED_KMH) {
                log.info("Geolocation inconsistency detected for event with ID {}", eventId);
                return RuleConfigUtils.buildResult(eventId, RULE_NAME, true, "Geolocation inconsistency detected");
            }

            log.debug("Geolocation is consistent with user's latest event with location for event with ID {}", eventId);
        } else {
            log.debug("No past events with location found for user {}", username);
        }

        return RuleConfigUtils.buildResult(eventId, RULE_NAME, false, "Geolocation is consistent with user's history");
    }
}
