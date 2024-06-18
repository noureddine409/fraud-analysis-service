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
import ma.adria.eventanalyser.model.Account;
import ma.adria.eventanalyser.model.BeneficiaryInfo;
import ma.adria.eventanalyser.model.Event;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "gestionBenificiaire_event")
@SuperBuilder
public class BeneficiaryInfoEvent extends Event {
    private String action;
    @OneToOne(cascade = CascadeType.ALL)
    private BeneficiaryInfo beneficiaryInfo;
    @OneToOne(cascade = CascadeType.ALL)
    private Account compteDebitor;
}
