package ai.shreds.shared.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * DTO representing the result of GPS data processing
 */
@Getter
@Builder
@ToString
public class SharedProcessingResultDTO {
    private final boolean success;
    private final String message;
    private final String errorCode;
    private final UUID processedDataId;
    private final boolean shouldCloseConnection;

    /**
     * Factory method to create a successful result
     *
     * @param message Success message
     * @param processedDataId ID of the successfully processed data
     * @return A new SharedProcessingResultDTO indicating success
     */
    public static SharedProcessingResultDTO success(String message, UUID processedDataId) {
        return SharedProcessingResultDTO.builder()
                .success(true)
                .message(message)
                .processedDataId(processedDataId)
                .shouldCloseConnection(false)
                .build();
    }

    /**
     * Factory method to create a failure result
     *
     * @param message Error message
     * @param errorCode Error code identifying the type of failure
     * @param shouldCloseConnection Whether the connection should be closed
     * @return A new SharedProcessingResultDTO indicating failure
     */
    public static SharedProcessingResultDTO failure(String message, String errorCode, boolean shouldCloseConnection) {
        return SharedProcessingResultDTO.builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .shouldCloseConnection(shouldCloseConnection)
                .build();
    }
}