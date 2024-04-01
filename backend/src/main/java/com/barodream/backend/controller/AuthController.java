package com.barodream.backend.controller;

import com.barodream.backend.dto.LoginRequestDto;
import com.barodream.backend.service.CognitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CognitoService cognitoService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<String> signUp(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            SignUpResponse response = cognitoService.signUp(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
            return ResponseEntity.ok(response.sdkHttpResponse().statusText().orElse("Ok"));
        } catch (CognitoIdentityProviderException e) {
            return ResponseEntity.status(e.statusCode()).body(e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String accessToken = cognitoService.login(loginRequestDto.getUsername(),
                loginRequestDto.getPassword());
            return ResponseEntity.ok(accessToken);
        } catch (CognitoIdentityProviderException e) {
            return ResponseEntity.status(e.statusCode()).body(e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody String accessToken) {
        try {
            cognitoService.logout(accessToken);
            return ResponseEntity.ok("logout success");
        } catch (CognitoIdentityProviderException e) {
            return ResponseEntity.status(e.statusCode()).body(e.getLocalizedMessage());
        }
    }

}