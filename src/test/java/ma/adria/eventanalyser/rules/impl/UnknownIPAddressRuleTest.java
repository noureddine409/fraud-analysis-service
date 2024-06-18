package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.dto.events.AuthenticationEventDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnknownIPAddressRuleTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JPAStreamer jpaStreamer;

    @InjectMocks
    private UnknownIPAddressRule unknownIPAddressRule;

    private EventDto eventDto;
    private Event event;

    @BeforeEach
    void setUp() {
        Location location = Location.builder()
                .ipAddress("192.168.1.1")
                .build();

        eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .username("testUser")
                .location(LocationDto.builder()
                        .ipAddress("192.168.1.1")
                        .build())
                .build();

        event = Event.builder()
                .id(1L)
                .username("testUser")
                .location(location)
                .build();
    }

    @Test
    void testEvaluate_NullEventDto() {
        var result = unknownIPAddressRule.evaluate(null);
        assertFalse(result.isFraud());
        assertEquals("eventDto is null", result.getReason());
    }

    @Test
    void testEvaluate_InvalidEventData() {
        // Setup eventDto with null username and IP address
        eventDto = AuthenticationEventDto.builder()
                .id(1L)
                .username(null)
                .location(LocationDto.builder()
                        .ipAddress(null)
                        .build())
                .build();

        event = Event.builder()
                .id(1L)
                .username(null)
                .location(Location.builder()
                        .ipAddress(null)
                        .build())
                .build();
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        var result = unknownIPAddressRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("Invalid event data", result.getReason());
    }

    @Test
    void testEvaluate_KnownIPAddress() {
        Event existingEvent = Event.builder()
                .id(2L)
                .username("testUser")
                .location(Location.builder()
                        .ipAddress("192.168.1.1")
                        .build())
                .build();
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        // Simulate a stream of events that includes the existingEvent
        when(jpaStreamer.stream(Event.class))
                .thenReturn(Stream.of(existingEvent));

        var result = unknownIPAddressRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("IP address is known", result.getReason());
    }

    @Test
    void testEvaluate_UnknownIPAddress() {
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        // Simulate an empty stream indicating no matching IP addresses
        when(jpaStreamer.stream(Event.class))
                .thenReturn(Stream.empty());

        var result = unknownIPAddressRule.evaluate(eventDto);
        assertTrue(result.isFraud());
        assertEquals("Unknown IP address detected", result.getReason());
    }
}
