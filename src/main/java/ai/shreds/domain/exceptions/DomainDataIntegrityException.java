package ai.shreds.domain.exceptions;

/**
 * Exception thrown when data integrity validation fails in the domain layer.
 * This exception carries information about the specific data type and failure reason.
 */
public class DomainDataIntegrityException extends RuntimeException {
    private final String dataType;
    private final String failureReason;

    /**
     * Creates a new DomainDataIntegrityException with the specified data type and failure reason.
     *
     * @param dataType the type of data that failed validation
     * @param failureReason the specific reason for the validation failure
     */
    public DomainDataIntegrityException(String dataType, String failureReason) {
        super(String.format("Data integrity validation failed for %s: %s", dataType, failureReason));
        this.dataType = dataType;
        this.failureReason = failureReason;
    }

    /**
     * Gets the data type that failed validation.
     *
     * @return the data type
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Gets the specific failure reason.
     *
     * @return the failure reason
     */
    public String getFailureReason() {
        return failureReason;
    }
}