package ai.shreds.infrastructure.repositories;

import ai.shreds.infrastructure.entities.InfrastructureStockLevelJpaEntity;
import ai.shreds.infrastructure.entities.InfrastructureStockLevelId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InfrastructureStockLevelJpaRepository extends JpaRepository<InfrastructureStockLevelJpaEntity, InfrastructureStockLevelId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM InfrastructureStockLevelJpaEntity s WHERE s.itemId = :itemId AND s.locationId = :locationId")
    Optional<InfrastructureStockLevelJpaEntity> findByItemIdAndLocationIdForUpdate(@Param("itemId") UUID itemId, @Param("locationId") UUID locationId);

    Optional<InfrastructureStockLevelJpaEntity> findByItemIdAndLocationId(UUID itemId, UUID locationId);
}