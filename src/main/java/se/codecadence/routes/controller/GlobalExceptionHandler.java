package se.codecadence.routes.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        // Log this request to detect exploits.
        LOGGER.warning("NoH:" + ex.getRequestURL());
        return new ResponseEntity<>("The requested resource was not found. Look in the documentation if it is implemented.", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public String handleGeneralExceptions(Exception ex) {
        // Log the exception details
        LOGGER.log(Level.SEVERE, "An unexpected or unhandled error occurred: " + ex.getMessage(), ex);
        return "An error occurred. Please try again later.";
    }
}
