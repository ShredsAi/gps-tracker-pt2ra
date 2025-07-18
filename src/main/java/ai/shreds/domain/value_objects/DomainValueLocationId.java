package ai.shreds.domain.value_objects;

import java.util.Objects;
import java.util.UUID;

public final class DomainValueLocationId {
    private final UUID value;

    private DomainValueLocationId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Location ID cannot be null.");
        }
        this.value = value;
    }

    public static DomainValueLocationId from(UUID value) {
        return new DomainValueLocationId(value);
    }

    public static DomainValueLocationId from(String value) {
        return new DomainValueLocationId(UUID.fromString(value));
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainValueLocationId that = (DomainValueLocationId) o;
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