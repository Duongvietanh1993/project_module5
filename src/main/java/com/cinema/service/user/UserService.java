package com.cinema.service.user;

import com.cinema.model.dto.user.UserRegister;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.model.entity.User;

public interface UserService {
    //    User register(User user);
    UserRegister register(User user);

    UserResponseDTO login(UserRequestDTO userRequestDTO);
}
