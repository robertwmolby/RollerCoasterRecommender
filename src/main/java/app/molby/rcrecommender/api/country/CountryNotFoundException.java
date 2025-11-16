package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

public class CountryNotFoundException extends ResourceNotFoundException {
    public CountryNotFoundException(Long id) {
        super("Country", id);
    }
}
