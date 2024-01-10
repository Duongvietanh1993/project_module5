package com.cinema.model.dto.user.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseDTO {
    private String token;

    @NotEmpty(message = "Hãy điền tên đăng nhập")
    private String username;

    @NotEmpty(message = "Hãy điền email")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotEmpty(message = "Hãy điền tên người dùng")
    private String fullName;

    private Boolean status;
    private String image;
    private Set<String> roles;
}
