package com.w7.sweatlog_backend.entity.enums;

public enum AuthProvider {
    LOCAL,   // 자체 회원가입/로그인 (일반 계정)
    GOOGLE,  // 구글 OAuth2 로그인
    GITHUB   // 깃허브 OAuth2 로그인
}
