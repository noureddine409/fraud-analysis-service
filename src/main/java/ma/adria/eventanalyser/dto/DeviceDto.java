package ma.adria.eventanalyser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {
    private String fingerprint;
    private String deviceType;
    private String browser;
    private String browserVersion;
    private String os;
    private String manufacturer;
    private String model;

}
