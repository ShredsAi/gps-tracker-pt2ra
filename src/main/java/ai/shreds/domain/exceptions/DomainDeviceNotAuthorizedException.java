package ai.shreds.domain.exceptions;

/**
 * Exception thrown when a device is not authorized to send data to the system.
 * This exception carries both the device IMEI and its current status.
 */
public class DomainDeviceNotAuthorizedException extends RuntimeException {
    private final String imei;
    private final String deviceStatus;

    /**
     * Creates a new DomainDeviceNotAuthorizedException with the specified IMEI and device status.
     *
     * @param imei the IMEI of the unauthorized device
     * @param deviceStatus the current status of the device (e.g., "inactive", "unregistered")
     */
    public DomainDeviceNotAuthorizedException(String imei, String deviceStatus) {
        super(String.format("Device with IMEI '%s' is not authorized. Status: %s", imei, deviceStatus));
        this.imei = imei;
        this.deviceStatus = deviceStatus;
    }

    /**
     * Gets the IMEI of the unauthorized device.
     *
     * @return the device IMEI
     */
    public String getImei() {
        return imei;
    }

    /**
     * Gets the current status of the device.
     *
     * @return the device status
     */
    public String getDeviceStatus() {
        return deviceStatus;
    }
}