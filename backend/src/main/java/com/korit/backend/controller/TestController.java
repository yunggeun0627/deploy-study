package com.korit.backend.controller;

import com.korit.backend.entity.User;
import com.korit.backend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

    private final UserMapper userMapper;

    @GetMapping("/user")
    public ResponseEntity<?> get(@RequestParam Integer id) {
        System.out.println("!!!!");
        User user = userMapper.findById(id);
        System.out.println(user);
        return ResponseEntity.ok(user);
    }
}
