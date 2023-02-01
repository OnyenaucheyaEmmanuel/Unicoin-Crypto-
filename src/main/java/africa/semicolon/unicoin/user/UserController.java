package africa.semicolon.unicoin.user;


import africa.semicolon.unicoin.registration.dto.*;
import africa.semicolon.unicoin.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest) throws MessagingException {
        String resetForgotPassword = userService.forgetPassword(forgetPasswordRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(resetForgotPassword)
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String passwordReset = userService.resetPassword(resetPasswordRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(passwordReset)
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/confirmResetPassword")
    public ResponseEntity<?> confirmResetPasswordToken(@RequestBody ConfirmTokenRequest confirmTokenRequest) {
        String passwordReset = userService.confirmResetPasswordToken(confirmTokenRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(passwordReset)
                .timeStamp(ZonedDateTime.now())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("delete/{email}")
    public ResponseEntity<?> deleteToken(@PathVariable String email, @RequestBody PasswordRequest passwordRequest, HttpServletRequest httpServletRequest){
        String confirmToken = userService.deleteAccountByEmail(email, passwordRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(confirmToken)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        String confirmedLogin = userService.login(loginRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(confirmedLogin)
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}

