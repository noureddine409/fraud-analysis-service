package ma.adria.eventanalyser.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "geolocation")
public class Geolocation extends BaseEntity {
    private float longitude;
    private float latitude;

    @Builder
    public Geolocation(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, float longitude, float latitude) {
        super(id, createdAt, updatedAt);
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
