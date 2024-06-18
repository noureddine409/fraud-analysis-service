package ma.adria.eventanalyser.model;

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
@Table(name = "geolocation")
@SuperBuilder
public class Geolocation extends BaseEntity {
    private float longitude;
    private float latitude;

}
