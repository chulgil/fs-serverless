package com.barodream.backend.service;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.s3.bucket-name.goods}")
    private String bucketName;

    private final S3Client s3Client;

    public String uploadFile(MultipartFile file) throws IOException {
        String keyName = "uploads/" + file.getOriginalFilename(); // S3 내 파일 경로 및 이름 설정

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(keyName)
            .build();

        s3Client.putObject(putObjectRequest,
            RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return keyName;
    }

}
