package ai.shreds.domain.entities;

import ai.shreds.domain.value_objects.DomainValueItemId;
import ai.shreds.domain.value_objects.DomainValueLocationId;
import ai.shreds.domain.value_objects.DomainValueQuantity;

import java.time.LocalDateTime;

public class DomainStockLevelEntity {

    private final DomainValueItemId itemId;
    private final DomainValueLocationId locationId;
    private DomainValueQuantity quantityOnHand;
    private DomainValueQuantity reservedQuantity;
    private LocalDateTime lastUpdated;

    public DomainStockLevelEntity(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity quantityOnHand, DomainValueQuantity reservedQuantity, LocalDateTime lastUpdated) {
        this.itemId = itemId;
        this.locationId = locationId;
        this.quantityOnHand = quantityOnHand;
        this.reservedQuantity = reservedQuantity;
        this.lastUpdated = lastUpdated;
    }

    public DomainValueQuantity getAvailableQuantity() {
        return quantityOnHand.subtract(reservedQuantity);
    }

    public boolean canReserve(DomainValueQuantity quantityToReserve) {
        return getAvailableQuantity().isGreaterThanOrEqual(quantityToReserve);
    }

    public void reserveQuantity(DomainValueQuantity additionalQuantity) {
        if (!canReserve(additionalQuantity)) {
            throw new IllegalStateException("Insufficient stock to reserve quantity.");
        }
        this.reservedQuantity = this.reservedQuantity.add(additionalQuantity);
        this.lastUpdated = LocalDateTime.now();
    }

    public void releaseReservedQuantity(DomainValueQuantity quantityToRelease) {
        this.reservedQuantity = this.reservedQuantity.subtract(quantityToRelease);
        if (this.reservedQuantity.isLessThan(DomainValueQuantity.zero(this.reservedQuantity.getUnit()))) {
             this.reservedQuantity = DomainValueQuantity.zero(this.reservedQuantity.getUnit());
        }
        this.lastUpdated = LocalDateTime.now();
    }
    
    public void updateQuantityOnHand(DomainValueQuantity newQuantityOnHand) {
        this.quantityOnHand = newQuantityOnHand;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getStockLevelKey() {
        return this.itemId.toString() + ":" + this.locationId.toString();
    }

    public DomainValueItemId getItemId() {
        return itemId;
    }

    public DomainValueLocationId getLocationId() {
        return locationId;
    }

    public DomainValueQuantity getQuantityOnHand() {
        return quantityOnHand;
    }

    public DomainValueQuantity getReservedQuantity() {
        return reservedQuantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}