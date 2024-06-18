package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.CarteTransactionEvent;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class CarteTransactionEventDto extends EventDto {
    private String cardNumber;
    private String libelleOperation;
    private String dateOperation;
    private String devise;
    private String montant;
    private CarteTransactionEvent.CarteTransactionType carteTransactionType;
    private CarteTransactionEvent.TransationType transationType;
    private AccountDto compteDebit;
}
