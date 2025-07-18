package ai.shreds.domain.entities;

import ai.shreds.domain.exceptions.DomainInvalidReservationStateException;
import ai.shreds.domain.value_objects.*;

import java.time.Instant;
import java.util.Objects;

public class DomainComponentReservationEntity {

    private final DomainValueReservationId reservationId;
    private final DomainValueItemId itemId;
    private final DomainValueLocationId locationId;
    private final DomainValueProductionRunId productionRunId;
    private final DomainValueQuantity quantityReserved;
    private final DomainValueUserId reservedBy;
    private final Instant reservedAt;
    private final Instant expiresAt;
    private DomainValueReservationStatus status;

    public DomainComponentReservationEntity(DomainValueReservationId reservationId, DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueProductionRunId productionRunId, DomainValueQuantity quantityReserved, DomainValueUserId reservedBy, Instant reservedAt, Instant expiresAt, DomainValueReservationStatus status) {
        validateRequiredFields(reservationId, itemId, locationId, productionRunId, quantityReserved, reservedBy, reservedAt, expiresAt, status);
        validateQuantity(quantityReserved);
        validateReservationDates(reservedAt, expiresAt);

        this.reservationId = reservationId;
        this.itemId = itemId;
        this.locationId = locationId;
        this.productionRunId = productionRunId;
        this.quantityReserved = quantityReserved;
        this.reservedBy = reservedBy;
        this.reservedAt = reservedAt;
        this.expiresAt = expiresAt;
        this.status = status;
    }

    public void cancel() {
        if (!this.status.allowsModification()) {
            throw new DomainInvalidReservationStateException(this.reservationId, this.status, DomainValueReservationStatus.cancelled());
        }
        this.status = DomainValueReservationStatus.cancelled();
    }

    public void consume() {
        if (!this.status.allowsModification()) {
            throw new DomainInvalidReservationStateException(this.reservationId, this.status, DomainValueReservationStatus.consumed());
        }
        this.status = DomainValueReservationStatus.consumed();
    }

    public void expire() {
        if (!this.status.allowsModification()) {
            throw new DomainInvalidReservationStateException(this.reservationId, this.status, DomainValueReservationStatus.expired());
        }
        this.status = DomainValueReservationStatus.expired();
    }

    public boolean isExpired() {
        return this.status.isActive() && Instant.now().isAfter(this.expiresAt);
    }

    public boolean canBeModified() {
        return this.status.allowsModification();
    }

    public String getStockLevelKey() {
        return this.itemId.toString() + ":" + this.locationId.toString();
    }

    private void validateRequiredFields(Object... fields) {
        for (Object field : fields) {
            if (field == null) {
                throw new IllegalArgumentException("All reservation fields must be non-null.");
            }
        }
    }

    private void validateQuantity(DomainValueQuantity quantity) {
        if (!quantity.isPositive()) {
            throw new IllegalArgumentException("Reserved quantity must be positive.");
        }
    }

    private void validateReservationDates(Instant reservedAt, Instant expiresAt) {
        if (expiresAt.isBefore(reservedAt)) {
            throw new IllegalArgumentException("Expiration date cannot be before reservation date.");
        }
    }

    // Getters
    public DomainValueReservationId getReservationId() {
        return reservationId;
    }

    public DomainValueItemId getItemId() {
        return itemId;
    }

    public DomainValueLocationId getLocationId() {
        return locationId;
    }

    public DomainValueProductionRunId getProductionRunId() {
        return productionRunId;
    }

    public DomainValueQuantity getQuantityReserved() {
        return quantityReserved;
    }

    public DomainValueUserId getReservedBy() {
        return reservedBy;
    }

    public Instant getReservedAt() {
        return reservedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public DomainValueReservationStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainComponentReservationEntity that = (DomainComponentReservationEntity) o;
        return Objects.equals(reservationId, that.reservationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId);
    }
}