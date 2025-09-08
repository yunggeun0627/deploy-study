package com.korit.backend.service;

import com.korit.backend.dto.SignupDto;
import com.korit.backend.entity.User;
import com.korit.backend.mapper.UserMapper;
import com.korit.backend.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public User signup(SignupDto dto) throws BindException {
        User foundUser = userMapper.findByUsername(dto.getUsername());
        if (foundUser != null) {
            BindingResult bindingResult = new BeanPropertyBindingResult(foundUser, "");
            FieldError fieldError = new FieldError("nickName", "nickName", "이미 존재하는 사용자이름 입니다.");
            bindingResult.addError(fieldError);
            throw new BindException(bindingResult);
        }

        User signupUser = dto.toEntity(passwordEncoder);
        userMapper.insert(signupUser);
        jwtUtil.generateAccessToken(signupUser);
        return signupUser;
    }
}
