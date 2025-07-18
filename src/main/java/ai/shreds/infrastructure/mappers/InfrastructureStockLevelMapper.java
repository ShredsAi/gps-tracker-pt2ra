package ai.shreds.infrastructure.mappers;

import ai.shreds.domain.entities.DomainStockLevelEntity;
import ai.shreds.domain.value_objects.DomainValueItemId;
import ai.shreds.domain.value_objects.DomainValueLocationId;
import ai.shreds.infrastructure.entities.InfrastructureStockLevelJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InfrastructureStockLevelMapper {

    @Mapping(source = "itemId", target = "itemId", qualifiedByName = "uuidFromItemId")
    @Mapping(source = "locationId", target = "locationId", qualifiedByName = "uuidFromLocationId")
    @Mapping(source = "quantityOnHand.value", target = "quantityOnHand")
    @Mapping(source = "reservedQuantity.value", target = "reservedQty")
    @Mapping(target = "version", ignore = true)
    InfrastructureStockLevelJpaEntity toJpaEntity(DomainStockLevelEntity domainEntity);

    @Mapping(source = "itemId", target = "itemId", qualifiedByName = "itemIdFromUuid")
    @Mapping(source = "locationId", target = "locationId", qualifiedByName = "locationIdFromUuid")
    @Mapping(target = "quantityOnHand", expression = "java(ai.shreds.domain.value_objects.DomainValueQuantity.of(jpaEntity.getQuantityOnHand(), ai.shreds.domain.value_objects.DomainUnitOfMeasureValue.PIECES))")
    @Mapping(target = "reservedQuantity", expression = "java(ai.shreds.domain.value_objects.DomainValueQuantity.of(jpaEntity.getReservedQty(), ai.shreds.domain.value_objects.DomainUnitOfMeasureValue.PIECES))")
    DomainStockLevelEntity toDomainEntity(InfrastructureStockLevelJpaEntity jpaEntity);

    @Named("uuidFromItemId")
    default UUID uuidFromItemId(DomainValueItemId id) {
        return id.getValue();
    }

    @Named("itemIdFromUuid")
    default DomainValueItemId itemIdFromUuid(UUID uuid) {
        return DomainValueItemId.from(uuid);
    }

    @Named("uuidFromLocationId")
    default UUID uuidFromLocationId(DomainValueLocationId id) {
        return id.getValue();
    }

    @Named("locationIdFromUuid")
    default DomainValueLocationId locationIdFromUuid(UUID uuid) {
        return DomainValueLocationId.from(uuid);
    }
}