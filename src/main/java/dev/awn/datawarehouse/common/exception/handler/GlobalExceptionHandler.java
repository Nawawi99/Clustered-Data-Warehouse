package dev.awn.datawarehouse.common.exception.handler;

import dev.awn.datawarehouse.common.exception.DuplicateDealException;
import dev.awn.datawarehouse.common.exception.MissingBodyFieldsException;
import dev.awn.datawarehouse.common.exception.response.ErrorResponse;
import dev.awn.datawarehouse.core.deal.service.impl.DealServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateDealException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDealException(DuplicateDealException ex) {
        logger.info("into -> handleDuplicateDealException");

        String errorMessage = ex.getMessage();

        if (errorMessage == null || errorMessage.isEmpty()) {
            logger.warn("No error message was provided, falling back to default message");

            errorMessage = "A duplicate deal was encountered.";
        }

        logger.warn("Received DuplicateDealException with message/s: {}", ex.getMessage() == null || ex.getMessage()
                                                                                                       .isBlank() ? errorMessage : ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                List.of(errorMessage)
        );

        logger.info("Constructed the following ErrorMessage instance: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingBodyFieldsException.class)
    public ResponseEntity<ErrorResponse> handleMissingBodyFieldsException(MissingBodyFieldsException ex) {
        logger.info("into -> handleMissingBodyFieldsException");

        logger.warn("Received MissingBodyFieldsException with message/s: {}", ex.getMessages());

        List<String> errorMessages = ex.getMessages();

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        logger.info("Constructed the following ErrorMessage instance: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.info("into -> handleHttpMessageNotReadableException");

        logger.warn("Received HttpMessageNotReadableException with message/s: {}", ex.getMessage());

        String errorMessage = ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                List.of(errorMessage)
        );

        logger.info("Constructed the following ErrorMessage instance: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
