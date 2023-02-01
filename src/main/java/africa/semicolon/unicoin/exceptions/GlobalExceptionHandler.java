package africa.semicolon.unicoin.exceptions;

import africa.semicolon.unicoin.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiResponse> userAlreadyExistException(
        RegistrationException registrationException,
        HttpServletRequest httpServletRequest
    ){
        ApiResponse apiResponse = ApiResponse.builder().
                timeStamp(ZonedDateTime.now())
                .data(registrationException.getMessage())
                .path(httpServletRequest.getRequestURI())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public  ResponseEntity<ApiResponse> GenericHandler(Exception exception,
                                                       HttpServletRequest httpServletRequest){
        ApiResponse apiResponse = ApiResponse.builder().
                timeStamp(ZonedDateTime.now()).
                data(exception.getMessage()).
                path(httpServletRequest.getRequestURI()).
                statusCode(HttpStatus.BAD_REQUEST.value()).
                isSuccessful(false).build();
        return  new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
