package ma.adria.eventanalyser.dto.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.ContratDto;
import ma.adria.eventanalyser.dto.DeviceDto;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.model.Event;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class AuthenticationEventDto extends EventDto {

    @Builder

    public AuthenticationEventDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Event.Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, LocationDto location, ContratDto contrat, DeviceDto device, String motif) {
        super(id, createdAt, updatedAt, reference, timestamp, canal, activityTime, username, bankCode, countryCode, segment, location, contrat, device, motif);
    }
}
