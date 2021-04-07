package com.kavhome.equirontest.restservice.error;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:koljuchkin.aleksandr@alphaopen.com" >Aleksandr Kolyuchkin</a>
 */
public class ValidationErrorResponse {
    public List<Violation> getViolations() {
        return violations;
    }

    private final List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse() {
    }

}
