package africa.semicolon.unicoin.registration;

import africa.semicolon.unicoin.registration.dto.ConfirmTokenRequest;
import africa.semicolon.unicoin.registration.dto.RegistrationRequest;
import africa.semicolon.unicoin.registration.dto.ResendTokenRequest;
import africa.semicolon.unicoin.user.UserService;
import africa.semicolon.unicoin.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest,
                                                HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse apiResponse = ApiResponse.builder().
                statusCode(HttpStatus.OK.value()).
                data(registrationService.register(registrationRequest)).
                timeStamp(ZonedDateTime.now()).
                path(httpServletRequest.getRequestURI()).
                isSuccessful(true).build();

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
    @PostMapping("resend")
    public ResponseEntity<?> resendToken(@RequestBody ResendTokenRequest tokenRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        String resendTokenResponse = registrationService.resendToken(tokenRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(resendTokenResponse)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("confirm")
    public ResponseEntity<?> confirmToken(@RequestBody ConfirmTokenRequest token, HttpServletRequest httpServletRequest){
        String confirmToken = registrationService.confirmToken(token);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(confirmToken)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
