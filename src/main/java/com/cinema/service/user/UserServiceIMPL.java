package com.cinema.service.user;

import com.cinema.model.dto.user.UserRegisterDTO;
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
import java.util.Optional;
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

  /*  @Override
    public User register(User user) {
        //mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //role
        Set<Role> roles =new HashSet<>();
        //register của user thì coi là User
        if (user.getRoles()==null|| user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("USER"));
        }*//*else {
            //Tạo tài khoản và phân quyền admin
            user.getRoles().forEach(role -> {
                roles.add(roleService.findById(role.getId()));
            });
        }*//*
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setStatus(user.getStatus());
        newUser.setPassword(user.getPassword());
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }*/

    @Override
    public UserRegisterDTO register(UserRegisterDTO userRegisterDTO) {
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPassword);

        // Xử lý roles
        Set<Role> roles;
        if (userRegisterDTO.getRoles() == null || userRegisterDTO.getRoles().isEmpty()) {
            Role defaultRole = roleService.findByRoleName("USER");
            roles = new HashSet<>();
            roles.add(defaultRole);
        } else {
            roles = userRegisterDTO.getRoles();
        }

        // Tạo đối tượng User từ UserRegisterDTO
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(userRegisterDTO.getPassword());
        user.setEmail(userRegisterDTO.getEmail());
        user.setFullName(userRegisterDTO.getFullName());
        user.setStatus(userRegisterDTO.getStatus());
        user.setImage(userRegisterDTO.getImage());
        user.setRoles(roles);

        // Lưu người dùng vào cơ sở dữ liệu
        User savedUser = userRepository.save(user);

        // Trả về UserRegisterDTO đã đăng ký
        return new UserRegisterDTO(savedUser);
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Authentication authentication;
        authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(),userRequestDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return UserResponseDTO.builder().token(jwtProvider.generateToken(userPrinciple))
                .username(userPrinciple.getUsername())
                .fullName(userPrinciple.getUser().getFullName())
                .email(userPrinciple.getUser().getEmail())
                .status(userPrinciple.getUser().getStatus())
                .image(userPrinciple.getUser().getImage())
                .roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public UserResponseDTO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return UserResponseDTO.builder()
                    .username(user.getUsername())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .image(user.getImage())
                    .status(user.getStatus())
                    .build();
        }
       throw  new ClassCastException("Not Found");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
