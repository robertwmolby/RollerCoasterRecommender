package app.molby.rcrecommender.api.country;

import app.molby.rcrecommender.api.shared.ResourceNotFoundException;

public class CountryAccessNotFoundException extends ResourceNotFoundException {
    public CountryAccessNotFoundException(Long id) {
        super("Country access",id);
    }
}
