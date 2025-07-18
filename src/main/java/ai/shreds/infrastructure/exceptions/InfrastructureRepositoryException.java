package ai.shreds.infrastructure.exceptions;

/**
 * Exception thrown when repository operations fail in the infrastructure layer
 */
public class InfrastructureRepositoryException extends RuntimeException {

    private final String operation;
    private final String entityType;

    /**
     * Constructor with message, operation, and entity type
     * 
     * @param message The error message
     * @param operation The repository operation that failed
     * @param entityType The type of entity being operated on
     */
    public InfrastructureRepositoryException(String message, String operation, String entityType) {
        super(message);
        this.operation = operation;
        this.entityType = entityType;
    }

    /**
     * Constructor with message, operation, entity type, and cause
     * 
     * @param message The error message
     * @param operation The repository operation that failed
     * @param entityType The type of entity being operated on
     * @param cause The underlying cause of the exception
     */
    public InfrastructureRepositoryException(String message, String operation, String entityType, Throwable cause) {
        super(message, cause);
        this.operation = operation;
        this.entityType = entityType;
    }

    /**
     * Gets the repository operation that failed
     * 
     * @return The operation name
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Gets the entity type being operated on
     * 
     * @return The entity type
     */
    public String getEntityType() {
        return entityType;
    }

    @Override
    public String toString() {
        return "InfrastructureRepositoryException{" +
                "operation='" + operation + '\'' +
                ", entityType='" + entityType + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
