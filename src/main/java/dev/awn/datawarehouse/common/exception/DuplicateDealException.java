package dev.awn.datawarehouse.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateDealException extends RuntimeException {

    public DuplicateDealException() {
        super();
    }

    public DuplicateDealException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDealException(String message) {
        super(message);
    }

    public DuplicateDealException(Throwable cause) {
        super(cause);
    }
}