package com.lastdance.beeper.service.impl;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.lastdance.beeper.data.domain.User;
import com.lastdance.beeper.data.repository.UserRepository;
import com.lastdance.beeper.service.FCMService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FCMServiceImpl implements FCMService {
    private final UserRepository userRepository;

    public FCMServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getToken(Long userId, String token) throws IOException {
// 서비스 계정 키 파일의 경로
        FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

        // GoogleCredentials 객체 생성
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        // FirebaseOptions 객체 생성
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        // FirebaseApp 초기화
        FirebaseApp.initializeApp(options);

        // AccessToken을 얻어서 출력하는 코드
        credentials.refreshIfExpired();  // 만료되었으면 갱신
        AccessToken accessToken = credentials.getAccessToken();
        if (accessToken != null) {
            System.out.println(accessToken.getTokenValue());
        } else {
            System.out.println("Failed to retrieve access token.");
        }
        return accessToken.getTokenValue().toString();
    }
}
