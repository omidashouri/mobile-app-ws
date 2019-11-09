package ir.omidashouri.mobileappws.exceptions;

import ir.omidashouri.mobileappws.models.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {

//    is responsible for only one and that is UserServiceException
    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException exception,
                                                             WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(new Date(),exception.getMessage());

        return new ResponseEntity<>(errorMessage,
                                    new HttpHeaders(),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    handle all other exceptions
@ExceptionHandler(value = {Exception.class})
public ResponseEntity<Object> handleOtherException(Exception exception,
                                                         WebRequest request){

//  for security change 'exception.getMessage()' to 'other exception occurred'
    ErrorMessage errorMessage = new ErrorMessage(new Date(), exception.getMessage());

    return new ResponseEntity<>(errorMessage,
            new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR);
}
}
