package ma.adria.eventanalyser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {
    private String ipAddress;
    private String ipAddress2;
    private GeolocationDto geolocation;
}
