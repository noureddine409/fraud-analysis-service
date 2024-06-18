package ma.adria.eventanalyser.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "beneficiaryInfo")
@SuperBuilder
public class BeneficiaryInfo extends BaseEntity {
    private String nature;
    private String type;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
}
