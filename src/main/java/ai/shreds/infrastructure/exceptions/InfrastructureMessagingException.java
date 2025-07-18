package ai.shreds.infrastructure.exceptions;

/**
 * Exception thrown when messaging operations fail in the infrastructure layer
 */
public class InfrastructureMessagingException extends RuntimeException {

    private final String channelName;
    private final String messageId;

    /**
     * Constructor with message, channel name, and message ID
     * 
     * @param message The error message
     * @param channelName The name of the messaging channel
     * @param messageId The ID of the message that failed
     */
    public InfrastructureMessagingException(String message, String channelName, String messageId) {
        super(message);
        this.channelName = channelName;
        this.messageId = messageId;
    }

    /**
     * Constructor with message, channel name, message ID, and cause
     * 
     * @param message The error message
     * @param channelName The name of the messaging channel
     * @param messageId The ID of the message that failed
     * @param cause The underlying cause of the exception
     */
    public InfrastructureMessagingException(String message, String channelName, String messageId, Throwable cause) {
        super(message, cause);
        this.channelName = channelName;
        this.messageId = messageId;
    }

    /**
     * Gets the name of the messaging channel
     * 
     * @return The channel name
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * Gets the ID of the message that failed
     * 
     * @return The message ID
     */
    public String getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return "InfrastructureMessagingException{" +
                "channelName='" + channelName + '\'' +
                ", messageId='" + messageId + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
