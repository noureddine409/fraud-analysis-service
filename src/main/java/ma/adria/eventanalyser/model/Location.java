package ma.adria.eventanalyser.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "location")
@SuperBuilder
public class Location extends BaseEntity {
    private String ipAddress;
    private String ipAddress2;

    @ManyToOne(cascade = CascadeType.ALL)
    private Geolocation geolocation;

}
