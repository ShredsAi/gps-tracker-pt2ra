package ai.shreds.shared.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO containing the result of device validation
 */
@Getter
@Builder
@ToString
public class SharedDeviceValidationResult {
    private final String imei;
    private final boolean isAuthorized;
    private final boolean isActive;
    private final String manufacturer;
    private final String model;
    private final Instant lastCommunicationTimestamp;

    /**
     * Creates a ProcessingResultDTO based on the validation result
     *
     * @param success Whether the processing was successful
     * @param message Processing message or error description
     * @return SharedProcessingResultDTO with details from this validation
     */
    public SharedProcessingResultDTO toProcessingResult(boolean success, String message) {
        return SharedProcessingResultDTO.builder()
                .success(success)
                .message(message)
                .errorCode(success ? null : "DEVICE_" + (isAuthorized ? "INACTIVE" : "UNAUTHORIZED"))
                .processedDataId(success ? UUID.randomUUID() : null)
                .shouldCloseConnection(!isAuthorized || !isActive)
                .build();
    }
}