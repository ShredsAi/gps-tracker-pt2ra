package ai.shreds.adapter.exceptions;

import ai.shreds.shared.value_objects.SharedNetworkConnectionParams;

/**
 * Custom exception for network-related errors occurring in the adapter layer.
 * Captures context about the connection and provides error codes.
 */
public class AdapterNetworkException extends RuntimeException {

    private final String errorCode;
    private final SharedNetworkConnectionParams connectionParams;

    /**
     * Constructs a new AdapterNetworkException with the specified detail message, error code, and connection parameters.
     *
     * @param message          the detail message
     * @param errorCode        a code identifying the specific error type
     * @param connectionParams network connection parameters related to the exception
     */
    public AdapterNetworkException(String message, String errorCode, SharedNetworkConnectionParams connectionParams) {
        super(message);
        this.errorCode = errorCode;
        this.connectionParams = connectionParams;
    }

    /**
     * Constructs a new AdapterNetworkException with the specified detail message, error code,
     * connection parameters, and cause.
     *
     * @param message          the detail message
     * @param errorCode        a code identifying the specific error type
     * @param connectionParams network connection parameters related to the exception
     * @param cause            the cause of this exception
     */
    public AdapterNetworkException(String message, String errorCode, SharedNetworkConnectionParams connectionParams, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.connectionParams = connectionParams;
    }

    /**
     * @return the error code associated with this exception
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the network connection parameters associated with this exception
     */
    public SharedNetworkConnectionParams getConnectionParams() {
        return connectionParams;
    }
}
