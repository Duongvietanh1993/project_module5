package com.cinema.service.user;

import com.cinema.model.dto.user.UserRegisterDTO;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.model.entity.User;


public interface UserService {
        User register(User user);
    UserRegisterDTO register(UserRegisterDTO userRegisterDTO);

    UserResponseDTO login(UserRequestDTO userRequestDTO);
    UserResponseDTO findById(Long id);
    User findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
