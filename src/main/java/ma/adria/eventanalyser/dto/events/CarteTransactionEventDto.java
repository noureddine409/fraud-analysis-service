package ma.adria.eventanalyser.dto.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.dto.ContratDto;
import ma.adria.eventanalyser.dto.DeviceDto;
import ma.adria.eventanalyser.dto.LocationDto;
import ma.adria.eventanalyser.model.Event;
import ma.adria.eventanalyser.model.event.CarteTransactionEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class CarteTransactionEventDto extends EventDto {
    private String cardNumber;
    private String libelleOperation;
    private String dateOperation;
    private String devise;
    private String montant;
    private CarteTransactionEvent.CarteTransactionType carteTransactionType;
    private CarteTransactionEvent.TransationType transationType;
    private AccountDto compteDebit;

    @Builder
    public CarteTransactionEventDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String reference, LocalDateTime timestamp, Event.Canal canal, LocalDateTime activityTime, String username, String bankCode, String countryCode, String segment, LocationDto location, ContratDto contrat, DeviceDto device, String motif, String cardNumber, String libelleOperation, String dateOperation, String devise, String montant, CarteTransactionEvent.CarteTransactionType carteTransactionType, CarteTransactionEvent.TransationType transationType, AccountDto compteDebit) {
        super(id, createdAt, updatedAt, reference, timestamp, canal, activityTime, username, bankCode, countryCode, segment, location, contrat, device, motif);
        this.cardNumber = cardNumber;
        this.libelleOperation = libelleOperation;
        this.dateOperation = dateOperation;
        this.devise = devise;
        this.montant = montant;
        this.carteTransactionType = carteTransactionType;
        this.transationType = transationType;
        this.compteDebit = compteDebit;
    }
}
