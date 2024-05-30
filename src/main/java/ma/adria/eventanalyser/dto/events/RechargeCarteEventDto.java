package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.RechargeCarteEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class RechargeCarteEventDto extends EventDto {
    private AccountDto account;
    private AccountDto debitorAccount;
    private AccountDto creditorAccount;
    private String numCard;
    private String typeRecharge;
    private LocalDateTime dateExecution;
    private BigDecimal montant;
    private String devise;
    private String motif;
    private RechargeCarteEvent.RechargeCarteStatus status;

}
