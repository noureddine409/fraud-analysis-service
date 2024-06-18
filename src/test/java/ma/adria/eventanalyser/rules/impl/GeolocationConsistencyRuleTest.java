package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import ma.adria.eventanalyser.dto.GeolocationDto;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.AuthenticationEventDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.Geolocation;
import ma.adria.eventanalyser.model.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeolocationConsistencyRuleTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JPAStreamer jpaStreamer;

    @InjectMocks
    private GeolocationConsistencyRule geolocationConsistencyRule;

    @Test
    void testEvaluate_NullEventDto() {
        var result = geolocationConsistencyRule.evaluate(null);
        assertFalse(result.isFraud());
        assertEquals("eventDto is null", result.getReason());
    }

    @Test
    void testEvaluate_InvalidEventData() {
        EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .username(null)
                .location(LocationDto.builder()
                        .geolocation(null)
                        .build())
                .build();

        when(modelMapper.map(eventDto, Event.class)).thenReturn(Event.builder().build());

        var result = geolocationConsistencyRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("Invalid event data", result.getReason());
    }

    @Test
    void testEvaluate_ConsistentGeolocation() {
        GeolocationDto geoLocationDto = GeolocationDto.builder()
                .latitude(40.7128F)
                .longitude(-74.0060F)
                .build();

        EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .username("testUser")
                .location(LocationDto.builder()
                        .geolocation(geoLocationDto)
                        .build())
                .build();

        Event pastEvent = Event.builder()
                .id(2L)
                .username("testUser")
                .location(Location.builder()
                        .geolocation(Geolocation.builder()
                                .latitude(40.7128F)
                                .longitude(-74.0060F)
                                .build())
                        .build())
                .timestamp(LocalDateTime.now().minusDays(1))
                .build();

        // Mock ModelMapper to return Event with valid data
        Event event = Event.builder()
                .id(1L)
                .username("testUser")
                .location(Location.builder()
                        .geolocation(Geolocation.builder()
                                .latitude(40.7128F)
                                .longitude(-74.0060F)
                                .build())
                        .build())
                .timestamp(LocalDateTime.now())
                .build();
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        // Mock JPAStreamer to return the pastEvent in stream
        when(jpaStreamer.stream(Event.class))
                .thenReturn(Stream.of(pastEvent));

        var result = geolocationConsistencyRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("Geolocation is consistent with user's history", result.getReason());
    }


    @Test
    void testEvaluate_InconsistentGeolocation() {
        GeolocationDto geoLocationDto = GeolocationDto.builder()
                .latitude(40.7128F)
                .longitude(-74.0060F)
                .build();

        EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .username("testUser")
                .location(LocationDto.builder()
                        .geolocation(geoLocationDto)
                        .build())
                .timestamp(LocalDateTime.now())
                .build();

        Event pastEvent = Event.builder()
                .id(2L)
                .username("testUser")
                .location(Location.builder()
                        .geolocation(Geolocation.builder()
                                .latitude(34.0522F)
                                .longitude(-118.2437F)
                                .build())
                        .build())
                .timestamp(LocalDateTime.now().minusMinutes(30))
                .build();

        // Mock ModelMapper to return Event with valid data
        Event event = Event.builder()
                .id(1L)
                .username("testUser")
                .location(Location.builder()
                        .geolocation(Geolocation.builder()
                                .latitude(40.7128F)
                                .longitude(-74.0060F)
                                .build())
                        .build())
                .timestamp(LocalDateTime.now())
                .build();
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        // Mock JPAStreamer to return the pastEvent in stream
        when(jpaStreamer.stream(Event.class))
                .thenReturn(Stream.of(pastEvent));

        // Perform evaluation
        var result = geolocationConsistencyRule.evaluate(eventDto);

        // Assert results
        assertTrue(result.isFraud(), "Expected fraud detection but got false");
        assertEquals("Geolocation inconsistency detected", result.getReason(), "Unexpected reason for fraud detection");
    }



    @Test
    void testEvaluate_NoPastEventsWithLocation() {
        GeolocationDto geoLocationDto = GeolocationDto.builder()
                .latitude(40.7128F)
                .longitude(-74.0060F)
                .build();

        EventDto eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .username("testUser")
                .location(LocationDto.builder()
                        .geolocation(geoLocationDto)
                        .build())
                .build();

        Event event = Event.builder()
                .id(1L)
                .username("testUser")
                .location(Location.builder()
                        .geolocation(Geolocation.builder()
                                .latitude(40.7128F)
                                .longitude(-74.0060F)
                                .build())
                        .build())
                .timestamp(LocalDateTime.now())
                .build();

        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        when(jpaStreamer.stream(Event.class))
                .thenReturn(Stream.empty());

        var result = geolocationConsistencyRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("Geolocation is consistent with user's history", result.getReason());
    }
}
