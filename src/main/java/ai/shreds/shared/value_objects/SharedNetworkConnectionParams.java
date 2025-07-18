package ai.shreds.shared.value_objects;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.regex.Pattern;

/**
 * Value object representing network connection parameters
 */
@Getter
@ToString
public class SharedNetworkConnectionParams {
    private final String sourceIpAddress;
    private final Integer sourcePort;
    private final Integer destinationPort;
    private final String protocol;
    private final Instant connectionTimestamp;

    public SharedNetworkConnectionParams(String sourceIpAddress, Integer sourcePort, Integer destinationPort, String protocol, Instant connectionTimestamp) {
        this.sourceIpAddress = sourceIpAddress;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
        this.connectionTimestamp = connectionTimestamp;

        if (!validateIpAddress()) {
            throw new IllegalArgumentException("Invalid IP address format: " + sourceIpAddress);
        }
        if (!validatePorts()) {
            throw new IllegalArgumentException("Invalid port numbers. Ports must be between 1 and 65535");
        }
    }

    /**
     * Validates the IP address format
     *
     * @return true if the IP address is valid, false otherwise
     */
    public boolean validateIpAddress() {
        if (sourceIpAddress == null || sourceIpAddress.isEmpty()) {
            return false;
        }
        String ipPattern = "^((25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d?\\d)$";
        return Pattern.compile(ipPattern).matcher(sourceIpAddress).matches();
    }

    /**
     * Validates the port numbers
     *
     * @return true if both ports are within the valid range (1-65535), false otherwise
     */
    public boolean validatePorts() {
        return (sourcePort != null && sourcePort > 0 && sourcePort <= 65535) &&
               (destinationPort != null && destinationPort > 0 && destinationPort <= 65535);
    }
}