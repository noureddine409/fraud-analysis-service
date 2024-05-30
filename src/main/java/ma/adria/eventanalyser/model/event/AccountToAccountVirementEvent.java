package ma.adria.eventanalyser.model.event;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.model.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "accountToAccountVirement_event")
public class AccountToAccountVirementEvent extends Virement {
    @OneToOne(cascade = CascadeType.ALL)
    private Creditor creditor;

    public AccountToAccountVirementEvent(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, Location location, Contrat contrat, Device device, String motif, ExecutionDateType type, String curency, ExecutionFrequency executionFrequency, Creditor creditor) {
        super(id, createdAt, updatedAt, reference, timestamp, canal, activityTime, username, bankCode, countryCode, segment, location, contrat, device, motif, type, curency, executionFrequency);
        this.creditor = creditor;
    }
}
