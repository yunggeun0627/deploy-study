package com.korit.backend.security.handler;

import com.korit.backend.entity.User;
import com.korit.backend.mapper.UserMapper;
import com.korit.backend.security.jwt.JwtUtil;
import com.korit.backend.security.model.PrincipalUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        User user = userMapper.findByUsername(principalUser.getUsername());

        if (user == null) {
            userMapper.insert(
                    User.builder()
                            .username(principalUser.getUsername())
                            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .email(principalUser.getUser().getEmail())
                            .name(principalUser.getUser().getName())
                            .build());
        }

        user = userMapper.findByUsername(principalUser.getUsername());

        String accessToken = jwtUtil.generateAccessToken(user);
        response.sendRedirect("http://localhost:5173/oauth2/login?accessToken=" + accessToken);

    }
}