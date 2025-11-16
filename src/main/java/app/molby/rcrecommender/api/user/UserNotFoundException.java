package app.molby.rcrecommender.api.user;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String id) {
        super("User", id);
    }
}
