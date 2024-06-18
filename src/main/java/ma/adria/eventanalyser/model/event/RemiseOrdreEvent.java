package ma.adria.eventanalyser.model.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.model.Account;
import ma.adria.eventanalyser.model.Event;


@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "remiseOrdre_event")
@SuperBuilder
public class RemiseOrdreEvent extends Event {
    private String natureRemise;
    private String FormatRemiseOrdre;
    private String donneurOrdre;
    @OneToOne(cascade = CascadeType.ALL)
    private Account compteDebit;
    private String fichier;
    @Enumerated(EnumType.STRING)
    private AjouterRemiseStatus status;

    public enum AjouterRemiseStatus {
        SUCCES, FAILED
    }


}
