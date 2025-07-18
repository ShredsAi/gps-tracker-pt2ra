package ai.shreds.shared.dtos;

import ai.shreds.shared.enums.SharedProtocolTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO representing raw GPS data received from devices
 */
@Getter
@ToString
public class SharedRawGpsDataDTO {
    private final UUID id;
    private final String deviceImei;
    private final byte[] rawPayload;
    private final SharedProtocolTypeEnum protocolType;
    private final Instant receivedTimestamp;
    private final String sourceAddress;
    private final Integer sourcePort;
    private final Integer dataLength;

    @Builder(builderMethodName = "builder")
    public SharedRawGpsDataDTO(UUID id, String deviceImei, byte[] rawPayload, 
                              SharedProtocolTypeEnum protocolType, Instant receivedTimestamp,
                              String sourceAddress, Integer sourcePort, Integer dataLength) {
        this.id = id;
        this.deviceImei = deviceImei;
        this.rawPayload = rawPayload;
        this.protocolType = protocolType;
        this.receivedTimestamp = receivedTimestamp;
        this.sourceAddress = sourceAddress;
        this.sourcePort = sourcePort;
        this.dataLength = dataLength;
        
        validate();
    }

    private void validate() {
        if (deviceImei == null || deviceImei.length() != 15) {
            throw new IllegalArgumentException("Device IMEI must be exactly 15 digits");
        }
        if (rawPayload == null || rawPayload.length == 0) {
            throw new IllegalArgumentException("Raw payload cannot be empty");
        }
        if (protocolType == null) {
            throw new IllegalArgumentException("Protocol type cannot be null");
        }
        if (receivedTimestamp == null) {
            throw new IllegalArgumentException("Received timestamp cannot be null");
        }
        if (sourceAddress == null || sourceAddress.isEmpty()) {
            throw new IllegalArgumentException("Source address cannot be empty");
        }
        if (sourcePort == null || sourcePort <= 0 || sourcePort > 65535) {
            throw new IllegalArgumentException("Invalid source port");
        }
        if (dataLength == null || dataLength != rawPayload.length) {
            throw new IllegalArgumentException("Data length must match raw payload length");
        }
    }

    /**
     * Converts the DTO to a message string for logging or display
     *
     * @return A string representation of the GPS data
     */
    public String toMessage() {
        return String.format("GPS Data [ID: %s, IMEI: %s, Protocol: %s, Timestamp: %s, Source: %s:%d, Length: %d]",
                id, deviceImei, protocolType, receivedTimestamp, sourceAddress, sourcePort, dataLength);
    }
}