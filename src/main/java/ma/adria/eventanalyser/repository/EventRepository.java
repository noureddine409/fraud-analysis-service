package ma.adria.eventanalyser.repository;

import ma.adria.eventanalyser.model.Event;

/**
 * Repository interface for accessing event data.
 * This interface extends `ReadOnlyRepository` because this microservice does not have write permission on event entities.
 * You can use this repository to access specific event data or utilize {@link com.speedment.jpastreamer.application.JPAStreamer}.
 */
public interface EventRepository extends ReadOnlyRepository<Event, Long> {
}
