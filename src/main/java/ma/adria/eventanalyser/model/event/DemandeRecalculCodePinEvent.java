package ma.adria.eventanalyser.model.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.model.Account;
import ma.adria.eventanalyser.model.Event;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "demandeRecalculCodePin_event")
public class DemandeRecalculCodePinEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    private String carteId;
    @Enumerated(EnumType.STRING)
    private TransferMode modeEnvoie;
    @Enumerated(EnumType.STRING)
    private DemandeRecalculCodePinStatus status;

    public enum DemandeRecalculCodePinStatus {
        SUCCES, FAILED
    }

    public enum TransferMode {
        SMS, EMAIL
    }


}
