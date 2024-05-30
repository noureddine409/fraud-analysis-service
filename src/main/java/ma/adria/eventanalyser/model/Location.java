package ma.adria.eventanalyser.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location extends BaseEntity {
    private String ipAddress;
    private String ipAddress2;

    @ManyToOne(cascade = CascadeType.ALL)
    private Geolocation geolocation;

    @Builder
    public Location(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String ipAddress, String ipAddress2, Geolocation geolocation) {
        super(id, createdAt, updatedAt);
        this.ipAddress = ipAddress;
        this.ipAddress2 = ipAddress2;
        this.geolocation = geolocation;
    }
}
