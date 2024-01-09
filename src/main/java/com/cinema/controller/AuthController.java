package com.cinema.controller;

import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.model.entity.User;
import com.cinema.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO userResponseDTO = userService.login(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        return new ResponseEntity<>(userService.register(user),HttpStatus.OK);
    }

}