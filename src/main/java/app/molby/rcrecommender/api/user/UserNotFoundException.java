package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

/**
 * Exception thrown when a requested user cannot be found.
 *
 * <p>This is a specialization of {@link ResourceNotFoundException} that
 * automatically supplies the resource type {@code "User"} to the base
 * exception. It is typically used by service and controller layers when a
 * lookup operation fails to locate the specified user.</p>
 * @author Bob Molby
 */
public class UserNotFoundException extends ResourceNotFoundException {

    /**
     * Constructs a new {@code UserNotFoundException} for the given user ID.
     * @param id the identifier of the user that could not be found
     */
    public UserNotFoundException(String id) {
        super("User", id);
    }
}
