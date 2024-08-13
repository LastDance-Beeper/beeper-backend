package com.lastdance.beeper.controller;

import com.amazonaws.Response;
import com.lastdance.beeper.data.dto.PostTokenRequest;
import com.lastdance.beeper.service.FCMService;
import com.lastdance.beeper.service.impl.FCMServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;/* FCM Token 서버 저장 API */
import org.springframework.web.bind.annotation.RequestBody;
public class FCMController {
    private FCMServiceImpl fcmServiceimpl;
    public FCMController(FCMServiceImpl fcmServiceimpl) {
        this.fcmServiceimpl = fcmServiceimpl;
    }

    @PostMapping("/{userId}/token")
    public ResponseEntity<String> getToken(@PathVariable Long userId, @Valid @RequestBody PostTokenRequest postTokenReq) {
        try {
            // fcmServiceimpl.getToken 호출
            String token = fcmServiceimpl.getToken(userId, postTokenReq.getToken());

            // ResponseEntity를 사용하여 결과와 상태 코드를 반환
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}