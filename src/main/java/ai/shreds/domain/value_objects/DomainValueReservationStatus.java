package ai.shreds.domain.value_objects;

import java.util.Objects;

public final class DomainValueReservationStatus {

    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String CONSUMED_STATUS = "CONSUMED";
    public static final String EXPIRED_STATUS = "EXPIRED";
    public static final String CANCELLED_STATUS = "CANCELLED";

    private final String value;

    private DomainValueReservationStatus(String value) {
        this.value = value;
    }

    public static DomainValueReservationStatus active() {
        return new DomainValueReservationStatus(ACTIVE_STATUS);
    }

    public static DomainValueReservationStatus consumed() {
        return new DomainValueReservationStatus(CONSUMED_STATUS);
    }

    public static DomainValueReservationStatus expired() {
        return new DomainValueReservationStatus(EXPIRED_STATUS);
    }

    public static DomainValueReservationStatus cancelled() {
        return new DomainValueReservationStatus(CANCELLED_STATUS);
    }

    public static DomainValueReservationStatus from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Status value cannot be null");
        }
        switch (value.toUpperCase()) {
            case ACTIVE_STATUS: return active();
            case CONSUMED_STATUS: return consumed();
            case EXPIRED_STATUS: return expired();
            case CANCELLED_STATUS: return cancelled();
            default:
                throw new IllegalArgumentException("Invalid reservation status: " + value);
        }
    }

    public String getValue() {
        return value;
    }

    public boolean isActive() {
        return ACTIVE_STATUS.equals(this.value);
    }

    public boolean allowsModification() {
        return isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainValueReservationStatus that = (DomainValueReservationStatus) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}