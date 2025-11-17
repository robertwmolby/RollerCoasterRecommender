package app.molby.rcrecommender.api.shared;

/**
 * Base exception indicating that a requested resource could not be found.
 *
 * <p>This exception serves as a generic "not found" error for domain-specific
 * resources. Subclasses (e.g., {@code CoasterRatingNotFoundException}) may
 * provide more specific context, but all follow this common message format.</p>
 * @author Bob Molby
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new {@code ResourceNotFoundException} with a standardized
     * "{resourceName} with id {id} not found" message.
     *
     * @param resourceName the type or name of the resource (e.g., "Coaster rating", "User")
     * @param id the identifier of the resource that was not found
     */
    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " with id " + id + " not found");
    }
}
