package ai.shreds.infrastructure.repositories.impl;

import ai.shreds.domain.entities.DomainComponentReservationEntity;
import ai.shreds.domain.ports.DomainOutputPortReservationRepository;
import ai.shreds.domain.value_objects.*;
import ai.shreds.infrastructure.mappers.InfrastructureComponentReservationMapper;
import ai.shreds.infrastructure.repositories.InfrastructureComponentReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InfrastructureComponentReservationRepositoryImpl implements DomainOutputPortReservationRepository {

    private final InfrastructureComponentReservationJpaRepository jpaRepository;
    private final InfrastructureComponentReservationMapper mapper;

    @Override
    public DomainComponentReservationEntity save(DomainComponentReservationEntity reservation) {
        var jpaEntity = mapper.toJpaEntity(reservation);
        var savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    public DomainComponentReservationEntity update(DomainComponentReservationEntity reservation) {
        return save(reservation); // JPA's save method handles both create and update
    }

    @Override
    public Optional<DomainComponentReservationEntity> findById(DomainValueReservationId reservationId) {
        return jpaRepository.findById(reservationId.getValue()).map(mapper::toDomainEntity);
    }

    @Override
    public List<DomainComponentReservationEntity> findByProductionRunId(DomainValueProductionRunId productionRunId) {
        return jpaRepository.findByProductionRunId(productionRunId.getValue()).stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DomainComponentReservationEntity> findExpiredActiveReservations(Instant currentTime) {
        return jpaRepository.findByExpiresAtBeforeAndStatus(currentTime, DomainValueReservationStatus.ACTIVE_STATUS).stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DomainComponentReservationEntity> findActiveReservationsByItemAndLocation(DomainValueItemId itemId, DomainValueLocationId locationId) {
        return jpaRepository.findByItemIdAndLocationIdAndStatus(itemId.getValue(), locationId.getValue(), DomainValueReservationStatus.ACTIVE_STATUS).stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DomainValueQuantity getTotalReservedQuantity(DomainValueItemId itemId, DomainValueLocationId locationId) {
        // This should be implemented with a more efficient query in a real application
        return findActiveReservationsByItemAndLocation(itemId, locationId).stream()
                .map(DomainComponentReservationEntity::getQuantityReserved)
                .reduce(DomainValueQuantity.zero(DomainUnitOfMeasureValue.PIECES), DomainValueQuantity::add);
    }

    @Override
    public void delete(DomainValueReservationId reservationId) {
        jpaRepository.deleteById(reservationId.getValue());
    }

    @Override
    public boolean exists(DomainValueReservationId reservationId) {
        return jpaRepository.existsById(reservationId.getValue());
    }
}