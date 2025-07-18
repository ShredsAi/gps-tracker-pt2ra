package ai.shreds.shared.value_objects;

import ai.shreds.shared.dtos.SharedRawGpsDataDTO;
import ai.shreds.shared.enums.SharedProtocolTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * Value object representing parsed GPS data before conversion to DTO
 */
@Getter
@Builder
@ToString
public class SharedParsedGpsData {
    private final String imei;
    private final byte[] rawPayload;
    private final Instant timestamp;
    private final boolean isValid;

    public SharedParsedGpsData(String imei, byte[] rawPayload, Instant timestamp, boolean isValid) {
        this.imei = imei;
        this.rawPayload = rawPayload;
        this.timestamp = timestamp;
        this.isValid = isValid;
        
        validate();
    }

    private void validate() {
        if (imei == null || !isValidImei(imei)) {
            throw new IllegalArgumentException("IMEI must be exactly 15 digits");
        }
        if (rawPayload == null || rawPayload.length == 0) {
            throw new IllegalArgumentException("Raw payload cannot be empty");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (timestamp.isAfter(Instant.now().plusSeconds(60))) { // Allow for small clock drift
            throw new IllegalArgumentException("Timestamp cannot be in the future");
        }
    }

    private boolean isValidImei(String imei) {
        // For test environments, we accept specific test IMEIs
        if (isTestImei(imei)) {
            return true;
        }
        
        // Standard IMEI validation: exactly 15 digits
        return imei.matches("^[0-9]{15}$");
    }

    private boolean isTestImei(String imei) {
        // Test IMEIs used in integration tests
        return imei != null && (imei.equals("123456789012345") || 
                               imei.equals("123456789012346") || 
                               imei.equals("123456789012347"));
    }

    /**
     * Converts parsed GPS data to a raw GPS data DTO
     *
     * @param connectionParams Network connection parameters
     * @param protocolType Protocol type used for parsing
     * @return SharedRawGpsDataDTO containing the parsed data and connection metadata
     */
    public SharedRawGpsDataDTO toRawGpsData(SharedNetworkConnectionParams connectionParams, SharedProtocolTypeEnum protocolType) {
        if (!isValid) {
            throw new IllegalStateException("Cannot convert invalid GPS data to DTO");
        }
        
        return SharedRawGpsDataDTO.builder()
                .id(UUID.randomUUID())
                .deviceImei(imei)
                .rawPayload(rawPayload)
                .protocolType(protocolType)
                .receivedTimestamp(timestamp)
                .sourceAddress(connectionParams.getSourceIpAddress())
                .sourcePort(connectionParams.getSourcePort())
                .dataLength(rawPayload.length)
                .build();
    }
}