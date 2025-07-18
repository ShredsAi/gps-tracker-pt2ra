package ai.shreds.domain.value_objects;

import ai.shreds.shared.enums.SharedUnitOfMeasureEnum;

public enum DomainUnitOfMeasureValue {
    PIECES("pcs"),
    KILOGRAMS("kg"),
    LITERS("l"),
    METERS("m"),
    SQUARE_METERS("m2");

    private final String symbol;

    DomainUnitOfMeasureValue(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isCompatibleWith(DomainUnitOfMeasureValue other) {
        return this == other;
    }

    public static DomainUnitOfMeasureValue fromShared(SharedUnitOfMeasureEnum shared) {
        if (shared == null) return null;
        return DomainUnitOfMeasureValue.valueOf(shared.name());
    }

    public SharedUnitOfMeasureEnum toShared() {
        return SharedUnitOfMeasureEnum.valueOf(this.name());
    }
}