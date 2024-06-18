package ma.adria.eventanalyser.rules.impl;

import com.speedment.jpastreamer.application.JPAStreamer;
import ma.adria.eventanalyser.dto.DeviceDto;
import ma.adria.eventanalyser.dto.events.AuthenticationEventDto;
import ma.adria.eventanalyser.dto.events.EventDto;
import ma.adria.eventanalyser.model.Device;
import ma.adria.eventanalyser.model.Event;
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
class UnknownDeviceRuleTest {

    private static final String USERNAME = "testUser";
    private static final String DEVICE_FINGERPRINT = "deviceFingerprint";

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JPAStreamer jpaStreamer;

    @InjectMocks
    private UnknownDeviceRule unknownDeviceRule;

    private EventDto eventDto;
    private Event event;

    @BeforeEach
    void setUp() {
        eventDto = createEventDto(USERNAME, DEVICE_FINGERPRINT);
        event = createEvent(USERNAME, DEVICE_FINGERPRINT);
    }

    @Test
    void testEvaluate_NullEventDto() {
        var result = unknownDeviceRule.evaluate(null);
        assertFalse(result.isFraud());
        assertEquals("eventDto is null", result.getReason());
    }

    @Test
    void testEvaluate_InvalidEventData() {
        // Setup eventDto with null username and device fingerprint
        eventDto = createEventDto(null, null);
        event = createEvent(null, null);
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        var result = unknownDeviceRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("Invalid event data", result.getReason());
    }

    @Test
    void testEvaluate_KnownDevice() {
        Event existingEvent = createEvent(USERNAME, DEVICE_FINGERPRINT);
        existingEvent.setId(2L);
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        // Simulate a stream of events that includes the existingEvent
        when(jpaStreamer.stream(Event.class)).thenReturn(Stream.of(existingEvent));

        var result = unknownDeviceRule.evaluate(eventDto);
        assertFalse(result.isFraud());
        assertEquals("Device is known", result.getReason());
    }

    @Test
    void testEvaluate_UnknownDevice() {
        when(modelMapper.map(eventDto, Event.class)).thenReturn(event);

        // Simulate an empty stream indicating no matching devices
        when(jpaStreamer.stream(Event.class)).thenReturn(Stream.empty());

        var result = unknownDeviceRule.evaluate(eventDto);
        assertTrue(result.isFraud());
        assertEquals("Unknown Device detected", result.getReason());
    }

    private EventDto createEventDto(String username, String deviceFingerprint) {
        return AuthenticationEventDto.builder()
                .id(1L)
                .username(username)
                .device(DeviceDto.builder()
                        .fingerprint(deviceFingerprint)
                        .build())
                .build();
    }

    private Event createEvent(String username, String deviceFingerprint) {
        return Event.builder()
                .id(1L)
                .username(username)
                .device(Device.builder()
                        .fingerprint(deviceFingerprint)
                        .build())
                .build();
    }
}
