package ai.shreds.domain.ports;

import ai.shreds.domain.entities.DomainComponentReservationEntity;
import ai.shreds.domain.value_objects.DomainValueItemId;
import ai.shreds.domain.value_objects.DomainValueLocationId;
import ai.shreds.domain.value_objects.DomainValueProductionRunId;
import ai.shreds.domain.value_objects.DomainValueQuantity;
import ai.shreds.domain.value_objects.DomainValueReservationId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface DomainOutputPortReservationRepository {
    DomainComponentReservationEntity save(DomainComponentReservationEntity reservation);
    DomainComponentReservationEntity update(DomainComponentReservationEntity reservation);
    Optional<DomainComponentReservationEntity> findById(DomainValueReservationId reservationId);
    List<DomainComponentReservationEntity> findByProductionRunId(DomainValueProductionRunId productionRunId);
    List<DomainComponentReservationEntity> findExpiredActiveReservations(Instant currentTime);
    List<DomainComponentReservationEntity> findActiveReservationsByItemAndLocation(DomainValueItemId itemId, DomainValueLocationId locationId);
    DomainValueQuantity getTotalReservedQuantity(DomainValueItemId itemId, DomainValueLocationId locationId);
    void delete(DomainValueReservationId reservationId);
    boolean exists(DomainValueReservationId reservationId);
}