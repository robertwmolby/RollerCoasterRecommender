package app.molby.rcrecommender.api.coaster;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

public class RollerCoasterNotFoundException extends ResourceNotFoundException {
    public RollerCoasterNotFoundException(Long id) {
        super("Roller coaster",id);
    }
}
