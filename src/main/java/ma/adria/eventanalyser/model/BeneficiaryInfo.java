package ma.adria.eventanalyser.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "beneficiaryInfo")
public class BeneficiaryInfo extends BaseEntity{
    private String nature;
    private String type;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @Builder
    public BeneficiaryInfo(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String nature) {
        super(id, createdAt, updatedAt);
        this.nature = nature;
    }
}
