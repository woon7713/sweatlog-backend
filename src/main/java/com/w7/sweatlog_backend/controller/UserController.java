package com.w7.sweatlog_backend.controller;

import com.w7.sweatlog_backend.dto.AuthRequest;
import com.w7.sweatlog_backend.dto.AuthResponse;
import com.w7.sweatlog_backend.dto.RefreshTokenRequest;
import com.w7.sweatlog_backend.dto.RegisterRequest;
import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.entity.UserDto;
import com.w7.sweatlog_backend.service.AuthService;
import com.w7.sweatlog_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 모든 유저 목록 조회
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<UserDto> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(UserDto.from(currentUser));
    }


}
