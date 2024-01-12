package com.cinema.model.dto.user.response;

import com.cinema.model.entity.Role;
import com.cinema.model.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponseDTO {
    private String token;
    private String username;
    private String email;
    private String fullName;
    private Boolean status;
    private String image;
    private Set<String> roles;
    private String memberLever;
    private Integer scorePoints;

    public UserResponseDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.status = user.getStatus();
        this.image = user.getImage();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        this.memberLever = user.getMemberLevel().name();
        this.scorePoints = user.getScorePoints();
    }
}
