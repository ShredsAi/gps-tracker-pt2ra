package ai.shreds.infrastructure.mappers;

import ai.shreds.domain.entities.DomainComponentReservationEntity;
import ai.shreds.domain.value_objects.*;
import ai.shreds.infrastructure.entities.InfrastructureComponentReservationJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InfrastructureComponentReservationMapper {

    @Mapping(source = "reservationId", target = "reservationId", qualifiedByName = "uuidFromReservationId")
    @Mapping(source = "itemId", target = "itemId", qualifiedByName = "uuidFromItemId")
    @Mapping(source = "locationId", target = "locationId", qualifiedByName = "uuidFromLocationId")
    @Mapping(source = "productionRunId", target = "productionRunId", qualifiedByName = "uuidFromProductionRunId")
    @Mapping(source = "quantityReserved.value", target = "quantityReserved")
    @Mapping(source = "quantityReserved.unit", target = "quantityUnit")
    @Mapping(source = "reservedBy", target = "reservedBy", qualifiedByName = "uuidFromUserId")
    @Mapping(source = "status", target = "status", qualifiedByName = "stringFromStatus")
    @Mapping(target = "version", ignore = true)
    InfrastructureComponentReservationJpaEntity toJpaEntity(DomainComponentReservationEntity domainEntity);

    @Mapping(source = "reservationId", target = "reservationId", qualifiedByName = "reservationIdFromUuid")
    @Mapping(source = "itemId", target = "itemId", qualifiedByName = "itemIdFromUuid")
    @Mapping(source = "locationId", target = "locationId", qualifiedByName = "locationIdFromUuid")
    @Mapping(source = "productionRunId", target = "productionRunId", qualifiedByName = "productionRunIdFromUuid")
    @Mapping(target = "quantityReserved", expression = "java(ai.shreds.domain.value_objects.DomainValueQuantity.of(jpaEntity.getQuantityReserved(), ai.shreds.domain.value_objects.DomainUnitOfMeasureValue.valueOf(jpaEntity.getQuantityUnit())))")
    @Mapping(source = "reservedBy", target = "reservedBy", qualifiedByName = "userIdFromUuid")
    @Mapping(source = "status", target = "status", qualifiedByName = "statusFromString")
    DomainComponentReservationEntity toDomainEntity(InfrastructureComponentReservationJpaEntity jpaEntity);

    @Named("uuidFromReservationId")
    default UUID uuidFromReservationId(DomainValueReservationId id) {
        return id.getValue();
    }

    @Named("reservationIdFromUuid")
    default DomainValueReservationId reservationIdFromUuid(UUID uuid) {
        return DomainValueReservationId.from(uuid);
    }

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

    @Named("uuidFromProductionRunId")
    default UUID uuidFromProductionRunId(DomainValueProductionRunId id) {
        return id.getValue();
    }

    @Named("productionRunIdFromUuid")
    default DomainValueProductionRunId productionRunIdFromUuid(UUID uuid) {
        return DomainValueProductionRunId.from(uuid);
    }

    @Named("uuidFromUserId")
    default UUID uuidFromUserId(DomainValueUserId id) {
        return id.getValue();
    }

    @Named("userIdFromUuid")
    default DomainValueUserId userIdFromUuid(UUID uuid) {
        return DomainValueUserId.from(uuid);
    }

    @Named("stringFromStatus")
    default String stringFromStatus(DomainValueReservationStatus status) {
        return status.getValue();
    }

    @Named("statusFromString")
    default DomainValueReservationStatus statusFromString(String status) {
        return DomainValueReservationStatus.from(status);
    }
}