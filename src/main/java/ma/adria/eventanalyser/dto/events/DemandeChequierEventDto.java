package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.DemandeChequierEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)

public class DemandeChequierEventDto extends EventDto {
    private String idContrat;
    private AccountDto account;
    private int nombreCheqquier;
    private String typeChequier;
    private String devise;
    private LocalDateTime dateEnvoie;
    private DemandeChequierEvent.DemandeChequierStatus demandeChequierStatus;


}
