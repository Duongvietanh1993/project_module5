package com.cinema.model.dto.user;

import com.cinema.model.entity.Role;
import com.cinema.model.entity.User;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegister {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Boolean status;
    private String image;
    private Set<Role> roles;

    public UserRegister(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.status = user.getStatus();
        this.image = user.getImage();
        this.roles = user.getRoles();
    }
}
