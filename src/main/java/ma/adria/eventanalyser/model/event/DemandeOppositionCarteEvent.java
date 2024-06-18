package ma.adria.eventanalyser.model.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.model.Account;
import ma.adria.eventanalyser.model.Event;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "DemandeOppositionCarte_event")
@SuperBuilder
public class DemandeOppositionCarteEvent extends Event {
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    private LocalDateTime datePerte;
    private LocalDateTime dateEnvoie;
    @Enumerated(EnumType.STRING)
    private DemandeOppositionCarteStatus status;

    public enum DemandeOppositionCarteStatus {
        SUCCES, FAILED
    }
}
