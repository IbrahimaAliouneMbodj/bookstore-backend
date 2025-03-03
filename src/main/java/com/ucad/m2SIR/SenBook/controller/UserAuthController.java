package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.UserLoginDTO;
import com.ucad.m2SIR.SenBook.dto.UserRegisterDTO;
import com.ucad.m2SIR.SenBook.configuration.JwtService;
import com.ucad.m2SIR.SenBook.service.UserAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {
    private final UserAuthService userAuthService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserAuthController(UserAuthService userAuthService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userAuthService = userAuthService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> connection(@RequestBody UserLoginDTO user){
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication auth = authenticationManager.authenticate(token);
        boolean status = auth.isAuthenticated();

        if(status)
        {
            String jwtToken = jwtService.generateToken(user.getUsername());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }
        return  new ResponseEntity<>("failed", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<String> inscription(@RequestBody UserRegisterDTO user){
        boolean status = userAuthService.saveUtilisateur(user);
        if(status)
        {
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
