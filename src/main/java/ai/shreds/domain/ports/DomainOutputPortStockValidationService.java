package ai.shreds.domain.ports;

import ai.shreds.domain.value_objects.DomainValueItemId;
import ai.shreds.domain.value_objects.DomainValueLocationId;
import ai.shreds.domain.value_objects.DomainValueQuantity;

public interface DomainOutputPortStockValidationService {
    boolean isStockAvailable(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity requestedQuantity);
    DomainValueQuantity getAvailableQuantity(DomainValueItemId itemId, DomainValueLocationId locationId);
    void reserveStock(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity quantity);
    void releaseStock(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity quantity);
    boolean itemExistsAtLocation(DomainValueItemId itemId, DomainValueLocationId locationId);
}