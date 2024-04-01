package com.barodream.backend.service;

import com.barodream.backend.config.CognitoProperties;
import com.barodream.backend.entity.User;
import com.barodream.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminConfirmSignUpResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;
import software.amazon.awssdk.utils.ImmutableMap;

@Service
@RequiredArgsConstructor
public class CognitoService {

    private final CognitoProperties cognitoConfig;
    private final UserRepository userRepository;

    private final CognitoIdentityProviderClient cognitoClient = CognitoIdentityProviderClient.builder()
        .build();


    /**
     * 새로운 사용자를 AWS Cognito에 가입시킵니다.
     *
     * @param username 사용자의 이름
     * @param password 사용자의 비밀번호
     * @return 가입 결과에 대한 응답
     */
    public SignUpResponse signUp(String username, String password) {
        SignUpResponse signUpResponse = cognitoClient.signUp(
            signUpRequest -> signUpRequest.clientId(cognitoConfig.getClientId()).username(username)
                .password(password));

        AdminConfirmSignUpResponse confirmResponse = cognitoClient.adminConfirmSignUp(
            request -> request.userPoolId(cognitoConfig.getUserPoolId()).username(username));

        // 가입 요청이 성공한 경우에만 사용자 정보를 저장
        if (confirmResponse != null && confirmResponse.sdkHttpResponse().isSuccessful()) {
            userRepository.save(new User(username, password));
        }

        return signUpResponse;
    }

    /**
     * 사용자의 로그인을 처리하고 로그인 결과로 액세스 토큰을 반환합니다.
     *
     * @param username 사용자의 이름
     * @param password 사용자의 비밀번호
     * @return 로그인 결과에 대한 액세스 토큰
     */
    public String login(String username, String password) {
        return cognitoClient.initiateAuth(
            request -> request.authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .clientId(cognitoConfig.getClientId())
                .authParameters(ImmutableMap.of("USERNAME", username, "PASSWORD", password))
                .build()).authenticationResult().accessToken();
    }

    /**
     * 사용자의 로그아웃 처리
     */
    public void logout(String accessToken) {
        cognitoClient.globalSignOut(
            globalSignOutRequest -> globalSignOutRequest.accessToken(accessToken));
    }

}