package com.cinema.security.controller;

import com.cinema.model.dto.user.UserRegisterDTO;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.model.entity.User;
import com.cinema.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDTO userRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Lấy ra thông báo lỗi đầu tiên
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        UserResponseDTO userResponseDTO = userService.login(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult bindingResult) {
        // Kiểm tra nếu có lỗi validate
        if (bindingResult.hasErrors()) {
            // Lấy ra thông báo lỗi đầu tiên
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // Kiểm tra trùng lặp email
        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
            String errorMessage = "Email đã được sử dụng. Vui lòng chọn email khác.";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // Kiểm tra trùng lặp tên đăng nhập
        if (userService.existsByUsername(userRegisterDTO.getUsername())) {
            String errorMessage = "Tên đăng nhập đã được sử dụng. Vui lòng chọn tên đăng nhập khác.";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // Trả về phản hồi thành công với đối tượng UserResponseDTO
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.OK);
    }

}
