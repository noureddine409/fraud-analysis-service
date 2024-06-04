package ma.adria.eventanalyser.repository;

import ma.adria.eventanalyser.model.FraudCheckResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCheckResultRepository extends JpaRepository<FraudCheckResult, Long> {
}
