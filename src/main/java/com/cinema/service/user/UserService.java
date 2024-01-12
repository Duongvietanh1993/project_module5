package com.cinema.service.user;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.user.request.ChangePassword;
import com.cinema.model.dto.user.request.UserRegisterDTO;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    void register(UserRegisterDTO userRegisterDTO) throws CustomException;

    UserResponseDTO login(UserRequestDTO userRequestDTO) throws CustomException;

    UserResponseDTO findById(Long id) throws CustomException;

    Page<UserResponseDTO> findAllUser(String name, Pageable pageable);

    Boolean changeStatusUser(Long id) throws CustomException;

    Boolean changePassword(Long id, ChangePassword changePassword) throws CustomException;

}
