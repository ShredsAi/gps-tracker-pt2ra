package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainComponentReservationEntity;
import java.util.List;

public interface DomainOutputPortEventPublisher {
    void publishReservationCreated(DomainComponentReservationEntity reservation);
    void publishReservationCancelled(DomainComponentReservationEntity reservation);
    void publishReservationConsumed(DomainComponentReservationEntity reservation);
    void publishReservationExpired(DomainComponentReservationEntity reservation);
    void publishBatchReservationCreated(List<DomainComponentReservationEntity> reservations);
}