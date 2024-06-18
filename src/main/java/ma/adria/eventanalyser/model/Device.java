package ma.adria.eventanalyser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "device")
@SuperBuilder
public class Device extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String fingerprint;
    private String deviceType;
    private String browser;
    private String browserVersion;
    private String os;
    private String manufacturer;
    private String model;


}
