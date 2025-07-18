package ai.shreds.domain.exceptions;

import ai.shreds.shared.enums.SharedProtocolTypeEnum;

/**
 * Exception thrown when a protocol is not supported by the system.
 * This exception carries information about the unsupported protocol type and port.
 */
public class DomainProtocolNotSupportedException extends RuntimeException {
    private final SharedProtocolTypeEnum protocolType;
    private final Integer port;

    /**
     * Creates a new DomainProtocolNotSupportedException with the specified protocol type and port.
     *
     * @param protocolType the unsupported protocol type
     * @param port the port number where the protocol was attempted
     */
    public DomainProtocolNotSupportedException(SharedProtocolTypeEnum protocolType, Integer port) {
        super(String.format("Protocol '%s' is not supported on port %d", 
            protocolType != null ? protocolType.name() : "UNKNOWN", port));
        this.protocolType = protocolType;
        this.port = port;
    }

    /**
     * Gets the unsupported protocol type.
     *
     * @return the protocol type
     */
    public SharedProtocolTypeEnum getProtocolType() {
        return protocolType;
    }

    /**
     * Gets the port number where the protocol was attempted.
     *
     * @return the port number
     */
    public Integer getPort() {
        return port;
    }
}