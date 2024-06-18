package ma.adria.eventanalyser.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.dto.AccountDto;
import ma.adria.eventanalyser.model.event.DemandeLcnEvent;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
public class DemandeLcnEventDto extends EventDto {
    private int nombreLcn;
    private AccountDto account;
    private String typeLcn;
    private String devise;
    private LocalDateTime dateEnvoie;
    private DemandeLcnEvent.DemandeLcnStatus status;


}
