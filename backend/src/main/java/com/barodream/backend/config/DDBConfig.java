package com.barodream.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DDBConfig {
    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        // DynamoDB 클라이언트 생성
        return DynamoDbClient.builder()
            .region(Region.of(region)) // AWS 리전 설정
            .build();
    }



    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        // DynamoDB Enhanced Client 생성
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
    }
}
