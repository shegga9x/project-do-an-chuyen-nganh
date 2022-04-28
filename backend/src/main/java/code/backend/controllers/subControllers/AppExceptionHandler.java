package code.backend.controllers.subControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import code.backend.helpers.advice.CustomException;
import code.backend.helpers.payload.response.MessageResponse;

/**
 * AppExceptionHandler
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        String errMess = ex.getLocalizedMessage();
        if (errMess == null)
            errMess = ex.toString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(errMess));
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleAnyNullPointerException(NullPointerException ex, WebRequest request) {
        String errMess = ex.getLocalizedMessage();
        if (errMess == null)
            errMess = ex.toString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(errMess));
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> handleAnyNullPointerException(CustomException ex, WebRequest request) {
        String errMess = ex.getLocalizedMessage();
        if (errMess == null)
            errMess = ex.toString();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(errMess));
    }
}