package ai.shreds.domain.value_objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class DomainValueQuantity {
    private static final int DECIMAL_PLACES = 4;
    private final BigDecimal value;
    private final DomainUnitOfMeasureValue unit;

    private DomainValueQuantity(BigDecimal value, DomainUnitOfMeasureValue unit) {
        if (value == null) {
            throw new IllegalArgumentException("Quantity value cannot be null.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Quantity unit cannot be null.");
        }
        this.value = value.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        this.unit = unit;
    }

    public static DomainValueQuantity of(BigDecimal value, DomainUnitOfMeasureValue unit) {
        return new DomainValueQuantity(value, unit);
    }

    public static DomainValueQuantity zero(DomainUnitOfMeasureValue unit) {
        return new DomainValueQuantity(BigDecimal.ZERO, unit);
    }

    public BigDecimal getValue() {
        return value;
    }

    public DomainUnitOfMeasureValue getUnit() {
        return unit;
    }

    public DomainValueQuantity add(DomainValueQuantity other) {
        validateSameUnit(other);
        return new DomainValueQuantity(this.value.add(other.value), this.unit);
    }

    public DomainValueQuantity subtract(DomainValueQuantity other) {
        validateSameUnit(other);
        return new DomainValueQuantity(this.value.subtract(other.value), this.unit);
    }

    public boolean isGreaterThan(DomainValueQuantity other) {
        validateSameUnit(other);
        return this.value.compareTo(other.value) > 0;
    }

    public boolean isGreaterThanOrEqual(DomainValueQuantity other) {
        validateSameUnit(other);
        return this.value.compareTo(other.value) >= 0;
    }

    public boolean isLessThan(DomainValueQuantity other) {
        validateSameUnit(other);
        return this.value.compareTo(other.value) < 0;
    }

    public boolean isZero() {
        return this.value.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isPositive() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }

    private void validateSameUnit(DomainValueQuantity other) {
        if (!this.unit.isCompatibleWith(other.unit)) {
            throw new IllegalArgumentException("Cannot perform operations on quantities with different units: " + this.unit + " and " + other.unit);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainValueQuantity that = (DomainValueQuantity) o;
        return value.compareTo(that.value) == 0 && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

    @Override
    public String toString() {
        return value + " " + unit.getSymbol();
    }
}