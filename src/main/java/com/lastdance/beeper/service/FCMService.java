package com.lastdance.beeper.service;

import java.io.IOException;

public interface FCMService {
    String getToken(Long userId, String token) throws IOException;
 }
