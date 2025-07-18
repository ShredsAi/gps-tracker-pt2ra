package ai.shreds.infrastructure.repositories;

import ai.shreds.infrastructure.entities.InfrastructureComponentReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface InfrastructureComponentReservationJpaRepository extends JpaRepository<InfrastructureComponentReservationJpaEntity, UUID> {

    List<InfrastructureComponentReservationJpaEntity> findByProductionRunId(UUID productionRunId);

    List<InfrastructureComponentReservationJpaEntity> findByExpiresAtBeforeAndStatus(Instant expiresAt, String status);

    List<InfrastructureComponentReservationJpaEntity> findByItemIdAndLocationIdAndStatus(UUID itemId, UUID locationId, String status);
}