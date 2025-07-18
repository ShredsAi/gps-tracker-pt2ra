package ai.shreds.shared.enums;

import lombok.Getter;

/**
 * Enumeration of supported GPS device protocols
 */
@Getter
public enum SharedProtocolTypeEnum {
    TELTONIKA(5027),
    COBAN(5028),
    RUPTELA(5029),
    UNKNOWN(-1);

    private final int defaultPort;

    SharedProtocolTypeEnum(int defaultPort) {
        this.defaultPort = defaultPort;
    }

    /**
     * Identifies protocol type based on the connection port
     *
     * @param port The port number to check
     * @return The corresponding protocol type or UNKNOWN if not matched
     */
    public static SharedProtocolTypeEnum fromPort(Integer port) {
        if (port == null) {
            return UNKNOWN;
        }
        
        for (SharedProtocolTypeEnum protocol : values()) {
            if (protocol.getDefaultPort() == port) {
                return protocol;
            }
        }
        return UNKNOWN;
    }
}