package ma.adria.eventanalyser.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Virement extends Event {
    @Enumerated(EnumType.STRING)
    private ExecutionDateType type;
    private String curency;//devise
    @OneToOne(cascade = CascadeType.ALL)
    private ExecutionFrequency executionFrequency;


    public Virement(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, Location location, Contrat contrat, Device device, String motif, ExecutionDateType type, String curency, ExecutionFrequency executionFrequency) {
        super(id, createdAt, updatedAt, reference, timestamp, canal, activityTime, username, bankCode, countryCode, segment, location, contrat, device, motif);
        this.type = type;
        this.curency = curency;
        this.executionFrequency = executionFrequency;
    }

    public enum ExecutionDateType {
        Immediate, Deferred, Permanent
    }
}
