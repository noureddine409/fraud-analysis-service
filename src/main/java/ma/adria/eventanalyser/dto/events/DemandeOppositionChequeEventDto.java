package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.DemandeOppositionChequeEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class DemandeOppositionChequeEventDto extends EventDto {
    private AccountDto account;
    private String Beneficiare;
    private BigDecimal montant;
    private int numeroCheque;
    private int numeroPremierCheque;
    private String declarationPolice;
    private String motif;
    private LocalDateTime datePerte;
    private LocalDateTime dateEnvoie;
    private String statusCheque;
    private DemandeOppositionChequeEvent.DemandeOppositionChequeStatus status;


}
