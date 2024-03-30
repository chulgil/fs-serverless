package com.barodream.backend.contoller;

import com.barodream.backend.dto.LoginRequestDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final CognitoService cognitoService;

    @GetMapping (value = "/sign-up")
    public String signUp(@RequestBody LoginRequestDto loginRequestDto) {
        cognitoService.signUp(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return "sign-up";
    }


}