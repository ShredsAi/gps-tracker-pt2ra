package ai.shreds.infrastructure.repositories.impl;

import ai.shreds.domain.entities.DomainStockLevelEntity;
import ai.shreds.domain.ports.DomainOutputPortStockLevelRepository;
import ai.shreds.domain.value_objects.DomainValueItemId;
import ai.shreds.domain.value_objects.DomainValueLocationId;
import ai.shreds.infrastructure.mappers.InfrastructureStockLevelMapper;
import ai.shreds.infrastructure.repositories.InfrastructureStockLevelJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InfrastructureStockLevelRepositoryImpl implements DomainOutputPortStockLevelRepository {

    private final InfrastructureStockLevelJpaRepository jpaRepository;
    private final InfrastructureStockLevelMapper mapper;

    @Override
    public Optional<DomainStockLevelEntity> findByItemAndLocation(DomainValueItemId itemId, DomainValueLocationId locationId) {
        return jpaRepository.findByItemIdAndLocationId(itemId.getValue(), locationId.getValue())
                .map(mapper::toDomainEntity);
    }

    @Override
    public DomainStockLevelEntity save(DomainStockLevelEntity stockLevel) {
        var jpaEntity = mapper.toJpaEntity(stockLevel);
        var savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<DomainStockLevelEntity> lockForUpdate(DomainValueItemId itemId, DomainValueLocationId locationId) {
        return jpaRepository.findByItemIdAndLocationIdForUpdate(itemId.getValue(), locationId.getValue())
                .map(mapper::toDomainEntity);
    }
}