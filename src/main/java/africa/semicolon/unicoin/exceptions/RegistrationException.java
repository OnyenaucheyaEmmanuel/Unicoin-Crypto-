package africa.semicolon.unicoin.exceptions;

import africa.semicolon.unicoin.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String messages){
        super(messages);
    }
}
