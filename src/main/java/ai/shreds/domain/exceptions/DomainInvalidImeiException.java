package ai.shreds.domain.exceptions;

/**
 * Exception thrown when an IMEI validation fails in the domain layer.
 * This exception carries both the invalid IMEI value and the specific reason for the failure.
 */
public class DomainInvalidImeiException extends RuntimeException {
    private final String imei;
    private final String reason;

    /**
     * Creates a new DomainInvalidImeiException with the specified IMEI and reason.
     *
     * @param imei the invalid IMEI that caused the exception
     * @param reason the specific reason why the IMEI is invalid
     */
    public DomainInvalidImeiException(String imei, String reason) {
        super(String.format("Invalid IMEI '%s': %s", imei, reason));
        this.imei = imei;
        this.reason = reason;
    }

    /**
     * Gets the invalid IMEI value.
     *
     * @return the IMEI that caused the exception
     */
    public String getImei() {
        return imei;
    }

    /**
     * Gets the reason why the IMEI is invalid.
     *
     * @return the specific reason for the IMEI validation failure
     */
    public String getReason() {
        return reason;
    }
}