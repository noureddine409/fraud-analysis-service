package ma.adria.eventanalyser.model.event;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.model.Creditor;
import ma.adria.eventanalyser.model.Virement;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "mutipleVirement_event")

public class MutipleVirementEvent extends Virement {
    private int nombreOperations;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Creditor> creditors;

}
