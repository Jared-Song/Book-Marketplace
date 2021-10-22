package com.rmit.sept.login.web;

import static com.rmit.sept.login.security.SecurityConstant.TOKEN_PREFIX;

import javax.validation.Valid;

import com.rmit.sept.login.LoginApplication;
import com.rmit.sept.login.model.User;
import com.rmit.sept.login.model.UserStatus;
import com.rmit.sept.login.payload.JWTLoginSucessReponse;
import com.rmit.sept.login.payload.LoginRequest;
import com.rmit.sept.login.security.JwtTokenProvider;
import com.rmit.sept.login.services.MapValidationErrorService;
import com.rmit.sept.login.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/users")
public class LoginController {
    private static final Logger LOGGER = LogManager.getLogger(LoginApplication.class);

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // login using a username and password
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        LOGGER.trace("Authenticating login");
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null)
            return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        LOGGER.trace("Generating jwt token for authorised user");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
        try {
            User user = userService.findByUsername(loginRequest.getUsername());
            if (user.getUserStatus().equals(UserStatus.TERMINATED) || user == null) {
                LOGGER.trace("User has been terminated");
                return new ResponseEntity<String>("Your account has been terminated and is no longer available", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
        }
       
        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }
}
