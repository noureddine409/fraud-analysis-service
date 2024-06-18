package ma.adria.eventanalyser.model.event;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.model.Creditor;
import ma.adria.eventanalyser.model.Virement;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "accountToAccountVirement_event")
@SuperBuilder
public class AccountToAccountVirementEvent extends Virement {
    @OneToOne(cascade = CascadeType.ALL)
    private Creditor creditor;

}
