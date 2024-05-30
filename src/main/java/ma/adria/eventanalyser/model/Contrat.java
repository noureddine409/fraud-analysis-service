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
@Table(name = "contrat")
public class Contrat extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String contratId;

    @Builder
    public Contrat(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String contratID) {
        super(id, createdAt, updatedAt);
        this.contratId = contratID;
    }
}
