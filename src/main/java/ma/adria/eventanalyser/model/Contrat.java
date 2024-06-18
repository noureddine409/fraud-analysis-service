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
@Table(name = "contrat")
@SuperBuilder
public class Contrat extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String contratId;

}
