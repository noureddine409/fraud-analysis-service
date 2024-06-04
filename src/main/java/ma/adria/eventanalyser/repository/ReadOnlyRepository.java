package ma.adria.eventanalyser.repository;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, I> extends Repository<T, I> {
    Optional<T> findById(I id);
}
