package ai.shreds.application.exceptions;

import ai.shreds.shared.enums.SharedProtocolTypeEnum;

/**
 * Exception thrown when protocol-related errors occur in the application layer
 */
public class ApplicationProtocolException extends RuntimeException {
    
    private final SharedProtocolTypeEnum protocolType;
    private final String errorCode;
    
    /**
     * Creates a new ApplicationProtocolException with message, protocol type, and error code
     * 
     * @param message Exception message
     * @param protocolType Protocol type that caused the exception
     * @param errorCode Error code identifying the type of protocol failure
     */
    public ApplicationProtocolException(String message, SharedProtocolTypeEnum protocolType, String errorCode) {
        super(message);
        this.protocolType = protocolType;
        this.errorCode = errorCode;
    }
    
    /**
     * Creates a new ApplicationProtocolException with message, protocol type, error code, and cause
     * 
     * @param message Exception message
     * @param protocolType Protocol type that caused the exception
     * @param errorCode Error code identifying the type of protocol failure
     * @param cause The underlying cause of the exception
     */
    public ApplicationProtocolException(String message, SharedProtocolTypeEnum protocolType, String errorCode, Throwable cause) {
        super(message, cause);
        this.protocolType = protocolType;
        this.errorCode = errorCode;
    }
    
    /**
     * Gets the protocol type that caused the exception
     * 
     * @return Protocol type
     */
    public SharedProtocolTypeEnum getProtocolType() {
        return protocolType;
    }
    
    /**
     * Gets the error code
     * 
     * @return Error code string
     */
    public String getErrorCode() {
        return errorCode;
    }
}