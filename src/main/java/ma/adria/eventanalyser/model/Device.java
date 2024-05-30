package ma.adria.eventanalyser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "device")
public class Device extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String fingerprint;
    private String deviceType;
    private String browser;
    private String browserVersion;
    private String os;
    private String manufacturer;
    private String model;

    @Builder
    public Device(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String fingerprint, String deviceType, String browser, String browserVersion, String os, String manufacturer, String model) {
        super(id, createdAt, updatedAt);
        this.fingerprint = fingerprint;
        this.deviceType = deviceType;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.os = os;
        this.manufacturer = manufacturer;
        this.model = model;
    }
}
