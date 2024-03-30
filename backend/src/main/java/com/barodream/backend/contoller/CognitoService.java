package com.barodream.backend.contoller;

import com.barodream.backend.config.CognitoProperties;
import com.barodream.backend.entity.User;
import com.barodream.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

@Service
@RequiredArgsConstructor
public class CognitoService {

    private final CognitoProperties cognitoConfig;

    private final UserRepository userRepository;

    private final CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder().build();

    public SignUpResponse signUp(String username, String password) {
        userRepository.save(new User(username, password));
        cognitoClient.adminConfirmSignUp(adminConfirmSignUpRequest -> adminConfirmSignUpRequest
                .username(username)
                .userPoolId(cognitoConfig.getUserPoolId()));
        return cognitoClient.signUp(signUpRequest -> signUpRequest
                .clientId(cognitoConfig.getClientId())
                .username(username)
                .password(password));
    }

}
