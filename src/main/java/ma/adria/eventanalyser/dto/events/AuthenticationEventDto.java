package ma.adria.eventanalyser.dto.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.ContratDto;
import ma.adria.eventanalyser.dto.DeviceDto;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.model.Event;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class AuthenticationEventDto extends EventDto {

}
