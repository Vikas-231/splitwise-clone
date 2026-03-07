package com.splitwise.clone.exception;

import com.splitwise.clone.model.exception.ValidationError;
import io.jsonwebtoken.lang.Assert;

import java.util.List;

public class DataValidationException extends Exception {

    private static final long serialVersionUID = -3705399168103355592L;

    private final List<ValidationError> errors;

    public DataValidationException(String message, List<ValidationError> error) {
        super(message);
        Assert.notNull(message, "message must not be null");
        Assert.notNull(error, "Validation errors cannot be null");
        this.errors = error;
    }

    public DataValidationException(List<ValidationError> errors) {
        super("Data Validation Failed");
        this.errors = errors;
    }

    public DataValidationException(ValidationError error) {
        super("Data Validation Failed");
        this.errors = List.of(error);
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
