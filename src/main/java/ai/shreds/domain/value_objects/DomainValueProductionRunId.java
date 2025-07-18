package ai.shreds.domain.value_objects;

import java.util.Objects;
import java.util.UUID;

public final class DomainValueProductionRunId {
    private final UUID value;

    private DomainValueProductionRunId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Production Run ID cannot be null.");
        }
        this.value = value;
    }

    public static DomainValueProductionRunId from(UUID value) {
        return new DomainValueProductionRunId(value);
    }

    public static DomainValueProductionRunId from(String value) {
        return new DomainValueProductionRunId(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainValueProductionRunId that = (DomainValueProductionRunId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}