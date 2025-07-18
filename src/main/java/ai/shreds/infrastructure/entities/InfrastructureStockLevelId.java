package ai.shreds.infrastructure.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class InfrastructureStockLevelId implements Serializable {
    private UUID itemId;
    private UUID locationId;

    public InfrastructureStockLevelId() {
    }

    public InfrastructureStockLevelId(UUID itemId, UUID locationId) {
        this.itemId = itemId;
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfrastructureStockLevelId that = (InfrastructureStockLevelId) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(locationId, that.locationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, locationId);
    }
}