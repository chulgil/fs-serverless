package com.barodream.backend.controller;

import com.barodream.backend.dto.LoginRequestDto;
import com.barodream.backend.entity.User;
import com.barodream.backend.repository.UserRepository;
import com.barodream.backend.service.CognitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CognitoService cognitoService;

    @PostMapping(value = "/sign-up")
    @ResponseBody
    public String signUp(@RequestBody LoginRequestDto loginRequestDto){
        cognitoService.signUp(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return "sign-up";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(@RequestBody LoginRequestDto loginRequestDto){
        return cognitoService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
    }

    @PostMapping(value = "/logout")
    @ResponseBody
    public String logout(@RequestBody String accessToken){
        cognitoService.logout(accessToken);
        return "logout";
    }

    @PostMapping(value = "/check")
    @ResponseBody
    public String check(@RequestBody String accessToken){
        cognitoService.logout(accessToken);
        return "logout";
    }
}
