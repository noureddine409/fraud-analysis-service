package ma.adria.eventanalyser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "account")
@SuperBuilder
public class Account extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private String accountHolder;
}
