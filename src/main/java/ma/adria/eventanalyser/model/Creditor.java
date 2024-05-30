package ma.adria.eventanalyser.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "creditor")
public class Creditor extends BaseEntity{
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;
    private String amount;

    @Builder
    public Creditor(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Account account) {
        super(id, createdAt, updatedAt);
        this.account = account;
    }
}
