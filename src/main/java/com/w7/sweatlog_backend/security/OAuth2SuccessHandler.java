package com.w7.sweatlog_backend.security;

import com.w7.sweatlog_backend.entity.User;
import com.w7.sweatlog_backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServiceException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        //구글
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String avatarUrl = oAuth2User.getAttribute("picture");

        // 깃허브는 이메일이 없을수도있어 없으면 그에 대한 고유성 보장하는 "@github.local처리
        if (email == null && oAuth2User.getAttribute("login") != null) {
            email = oAuth2User.getAttribute("login") + "@github.local";
            avatarUrl = oAuth2User.getAttribute("avatar_url");
        }

        //final -> 확정을 짓는것
        final String finalEmail = email;
        final String finalName = name != null ? name : "User";
        final String finalAvatarUrl = avatarUrl;

        //가입이 되어있으면 기존정보 가져오거나
        User user = userRepository.findByEmail(finalEmail)
                //안되어있으면 가입하는 것
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(finalEmail);
                    newUser.setUsername(generateUsername(finalEmail));
                    newUser.setFullName(finalName);
                    newUser.setProfileImageUrl(finalAvatarUrl);
                    newUser.setPassword("");
                    newUser.setEnabled(true);
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(newUser);
                });

        if (finalAvatarUrl != null && !finalAvatarUrl.equals(user.getProfileImageUrl())) {
            user.setProfileImageUrl(finalAvatarUrl);
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        //토큰 생성
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        String targetUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/callback")
                .queryParam("token", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String generateUsername(String email) {
        String baseUsername = email.split("@")[0].toLowerCase().replaceAll("[^a-z0-9]", "");
        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }
//counter를 붙임으로써 고유성 보장
        return username;
    }
}