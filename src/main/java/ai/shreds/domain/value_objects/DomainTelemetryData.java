package ai.shreds.domain.value_objects;

import ai.shreds.shared.enums.SharedEngineStatusEnum;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class DomainTelemetryData {
    Integer batteryLevel;
    Integer signalStrength;
    SharedEngineStatusEnum engineStatus;
    Double fuelLevel;
    Double temperature;
    Long mileage;
    Map<String, Boolean> digitalInputs;
    Map<String, Double> analogInputs;

    public boolean isValid() {
        return validateBatteryLevel() &&
               validateSignalStrength() &&
               validateFuelLevel() &&
               validateTemperature() &&
               validateMileage() &&
               validateDigitalInputs() &&
               validateAnalogInputs();
    }

    private boolean validateBatteryLevel() {
        return batteryLevel == null || (batteryLevel >= 0 && batteryLevel <= 100);
    }

    private boolean validateSignalStrength() {
        return signalStrength == null || signalStrength <= 0; // dBm values are negative or zero
    }

    private boolean validateFuelLevel() {
        return fuelLevel == null || (fuelLevel >= 0 && fuelLevel <= 100);
    }

    private boolean validateTemperature() {
        return temperature == null || (temperature >= -50 && temperature <= 100);
    }

    private boolean validateMileage() {
        return mileage == null || mileage >= 0;
    }

    private boolean validateDigitalInputs() {
        if (digitalInputs == null) return true;
        return digitalInputs.values().stream().allMatch(value -> value != null);
    }

    private boolean validateAnalogInputs() {
        if (analogInputs == null) return true;
        return analogInputs.values().stream().allMatch(value -> value != null && !value.isNaN() && !value.isInfinite());
    }
}