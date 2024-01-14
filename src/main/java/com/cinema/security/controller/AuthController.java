package com.cinema.security.controller;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.user.request.ChangePassword;
import com.cinema.model.dto.user.request.UserRegisterDTO;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequestDTO userRequestDTO) throws CustomException {
        UserResponseDTO userResponseDTO = userService.login(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws CustomException {
        userService.register(userRegisterDTO);
        String successMessage = "Bạn đã đăng ký tài khoản thành công mời đăng nhập!";
        // Trả về phản hồi thành công với đối tượng UserResponseDTO
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @PatchMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePassword changePassword) throws CustomException {
        String successMessage = null ;
        if (userService.changePassword(id, changePassword)) {
            successMessage = "Đổi mật khẩu thành công!";
        }
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
