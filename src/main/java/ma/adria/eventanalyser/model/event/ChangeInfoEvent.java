package ma.adria.eventanalyser.model.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.adria.eventanalyser.model.Event;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "changeInfo_event")
@SuperBuilder
public class ChangeInfoEvent extends Event {
    private boolean emailChanged;
    private boolean phoneChanged;
    private boolean adressChanged;
    private boolean contratChanged;
    private boolean passwordChanged;
    private boolean forgotPassword;

}
