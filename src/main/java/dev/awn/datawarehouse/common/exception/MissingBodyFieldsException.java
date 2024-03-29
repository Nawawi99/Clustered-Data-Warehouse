package dev.awn.datawarehouse.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingBodyFieldsException extends RuntimeException {
    private final BindingResult errors;

    public MissingBodyFieldsException(BindingResult errors) {
        this.errors = errors;
    }

    public List<String> getMessages() {
        return getValidationMessage(this.errors);
    }


    @Override
    public String getMessage() {
        return this.getMessages().toString();
    }

    private static List<String> getValidationMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                            .stream()
                            .map(MissingBodyFieldsException::getValidationMessage)
                            .collect(Collectors.toList());
    }

    private static String getValidationMessage(ObjectError error) {
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            return fieldError.getDefaultMessage();
        }
        return String.format("%s: %s\n", error.getObjectName(), error.getDefaultMessage());
    }
}