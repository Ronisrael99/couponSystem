package Ron.example.CouponProject_Fase_2.controllers;

import Ron.example.CouponProject_Fase_2.controllers.models.errors.ErrorResponse;
import Ron.example.CouponProject_Fase_2.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unAuthorizationHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.UNAUTHORIZED.value()).message("UnAuthorized").build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(ObjectNotExistException.class)
    public ResponseEntity<ErrorResponse> ObjectNotExistHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> objectAlreadyExistHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).status(HttpStatus.CONFLICT.value()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({CannotUpdateOrDeleteException.class, CannotAddException.class})
    public ResponseEntity<ErrorResponse> cannotUpdateOrDeleteHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
