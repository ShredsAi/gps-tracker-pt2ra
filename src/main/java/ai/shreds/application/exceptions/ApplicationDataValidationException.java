package ai.shreds.application.exceptions;

/**
 * Exception thrown when data validation fails in the application layer
 */
public class ApplicationDataValidationException extends RuntimeException {
    
    private final String errorCode;
    private final String fieldName;
    
    /**
     * Creates a new ApplicationDataValidationException with message and error code
     * 
     * @param message Exception message
     * @param errorCode Error code identifying the type of validation failure
     */
    public ApplicationDataValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.fieldName = null;
    }
    
    /**
     * Creates a new ApplicationDataValidationException with message, error code, and field name
     * 
     * @param message Exception message
     * @param errorCode Error code identifying the type of validation failure
     * @param fieldName Name of the field that failed validation
     */
    public ApplicationDataValidationException(String message, String errorCode, String fieldName) {
        super(message);
        this.errorCode = errorCode;
        this.fieldName = fieldName;
    }
    
    /**
     * Gets the error code
     * 
     * @return Error code string
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Gets the field name that failed validation
     * 
     * @return Field name or null if not specified
     */
    public String getFieldName() {
        return fieldName;
    }
}