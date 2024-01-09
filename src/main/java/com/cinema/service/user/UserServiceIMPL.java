package com.cinema.service.user;

import com.cinema.model.dto.user.UserRegister;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.model.entity.Role;
import com.cinema.model.entity.User;
import com.cinema.repository.UserRepository;
import com.cinema.security.jwt.JWTProvider;
import com.cinema.security.user_principle.UserPrinciple;
import com.cinema.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService{
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private AuthenticationProvider authenticationProvider;
   @Autowired
   private JWTProvider jwtProvider;
   @Autowired
   private RoleService roleService;
   /* @Override
    public User register(User user) {
        //mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //role
        Set<Role> roles =new HashSet<>();
        //register của user thì coi là User
        if (user.getRoles()==null|| user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("USER"));
        }else {
            //Tạo tài khoản và phân quyền admin
            user.getRoles().forEach(role -> {
                roles.add(roleService.findById(role.getId()));
            });
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setStatus(user.getStatus());
        newUser.setPassword(user.getPassword());
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }*/

    @Override
    public UserRegister register(User user) {
        //mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //role
        Set<Role> roles =new HashSet<>();
        //register của user thì coi là User
        if (user.getRoles()==null|| user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("USER"));
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setStatus(user.getStatus());
        newUser.setImage(user.getImage());
        newUser.setRoles(roles);
        userRepository.save(newUser);
        return new UserRegister(newUser);
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Authentication authentication;
        authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(),userRequestDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return UserResponseDTO.builder().token(jwtProvider.generateToken(userPrinciple))
                .username(userPrinciple.getUsername())
                .roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
