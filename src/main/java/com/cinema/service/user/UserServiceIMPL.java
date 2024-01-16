package com.cinema.service.user;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.user.request.ChangePassword;
import com.cinema.model.dto.user.request.UserRegisterDTO;
import com.cinema.model.dto.user.request.UserRequestDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.model.entity.Role;
import com.cinema.model.entity.User;
import com.cinema.model.entity.enums.MemberLevel;
import com.cinema.repository.UserRepository;
import com.cinema.security.jwt.JWTProvider;
import com.cinema.security.user_principle.UserPrinciple;
import com.cinema.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService {
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


    @Override
    public void register(UserRegisterDTO userRegisterDTO) throws CustomException {
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(userRegisterDTO.getPassword());
        userRegisterDTO.setPassword(encodedPassword);

        // Xử lý roles
        Set<Role> roles = new HashSet<>();
        if (userRegisterDTO.getRoles() == null || userRegisterDTO.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName("USER"));
        }

        MemberLevel memberLevel = null;
        // neu khong co hang dc them vao , mac dinh la hang bac
        if (userRegisterDTO.getMemberLever() == null || userRegisterDTO.getMemberLever().isEmpty()) {
            memberLevel = MemberLevel.BRONZE;
        }

        // Kiểm tra trùng lặp email
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new CustomException("Email đã được sử dụng. Vui lòng chọn email khác.");
        }

        // Kiểm tra trùng lặp tên đăng nhập
        if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            throw new CustomException("Tên đăng nhập đã được sử dụng. Vui lòng chọn tên đăng nhập khác.");
        }

        // Trả về UserRegisterDTO đã đăng ký
        userRepository.save(User.builder().username(userRegisterDTO.getUsername())
                .password(userRegisterDTO.getPassword())
                .email(userRegisterDTO.getEmail())
                .fullName(userRegisterDTO.getFullName())
                .status(true).image(userRegisterDTO.getImage())
                .roles(roles)
                .scorePoints(0)
                .memberLevel(memberLevel)
                .build());
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) throws CustomException {
        User user = userRepository.findByUsername(userRequestDTO.getUsername());

        if (user == null || !user.getStatus()) {
            throw new CustomException("Tài khoản không hợp lệ hoặc đã bị khóa. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu. Hoặc liên hệ với quản trị viên.");
        }

        try {
            Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userRequestDTO.getUsername(), userRequestDTO.getPassword()));
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

            return UserResponseDTO.builder().token(jwtProvider.generateToken(userPrinciple)).username(userPrinciple.getUsername()).fullName(userPrinciple.getUser().getFullName()).email(userPrinciple.getUser().getEmail()).status(userPrinciple.getUser().getStatus()).image(userPrinciple.getUser().getImage()).roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())).memberLever(userPrinciple.getUser().getMemberLevel().name()).build();
        } catch (AuthenticationException e) {
            throw new CustomException("Thông tin đăng nhập không hợp lệ. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.");
        }
    }

    @Override
    public UserResponseDTO findById(Long id) throws CustomException {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User not found"));
        return new UserResponseDTO(user);
    }

    @Override
    public Page<UserResponseDTO> findAllUser(String name, Pageable pageable) {
        Page<User> page;
        if (name.isEmpty()) {
            page = userRepository.findAll(pageable);
        } else {
            page = userRepository.findAllByUsernameContainingIgnoreCase(name, pageable);
        }
        return page.map(UserResponseDTO::new);
    }

    @Override
    public Boolean changeStatusUser(Long id) throws CustomException {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy tài khoản!"));
        user.setStatus(!user.getStatus());
        userRepository.save(user);
        return true;
    }

    @Override
    public Boolean changePassword(Long id, ChangePassword changePassword) throws CustomException {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy tài khoản!"));

        if (passwordEncoder.matches(changePassword.getOldPassword(), user.getPassword())) {
            if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
                String newPass = passwordEncoder.encode(changePassword.getNewPassword());
                user.setPassword(newPass);
                userRepository.save(user);
                return true;
            } else {
                throw new CustomException("Mật khẩu mới bị trùng với mật khẩu cũ ");
            }
        } else {
            throw new CustomException("Mật khẩu cũ không khớp");
        }
    }

}
