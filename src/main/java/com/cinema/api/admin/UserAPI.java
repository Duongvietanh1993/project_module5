package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class UserAPI {
    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<Page<UserResponseDTO>> userAll(@RequestParam(name = "keyword") String keyword,
                                                         @RequestParam(defaultValue = "5", name = "limit") int limit,
                                                         @RequestParam(defaultValue = "0", name = "page") int page,
                                                         @RequestParam(defaultValue = "id", name = "sort") String sort,
                                                         @RequestParam(defaultValue = "asc", name = "order") String order) {
        Pageable pageable;
        if (order.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<UserResponseDTO> userResponseDTOPage = userService.findAllUser(keyword, pageable);
        return new ResponseEntity<>(userResponseDTOPage, HttpStatus.OK);
    }
    @PatchMapping("/change-status-user/{id}")
    public ResponseEntity<?> changeStatusUser(@PathVariable("id") String id)throws CustomException{
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        userService.changeStatusUser(userId);
        String successMessage = "Bạn đã đổi trạng thái tài khoản thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PatchMapping("/add-role-to-user/{id}")
    public ResponseEntity<?> addRoleToUser(@PathVariable("id") String id, @ModelAttribute("roleName") String roleName) throws CustomException {
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        boolean result = userService.addRoleToUser(userId, roleName);
        if (result) {
            String successMessage = "Đã thêm quyền cho tài khoản thành công!";
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            String errorMessage = "Thêm quyền cho tài khoản thất bại!";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/remove-role-from-user/{id}")
    public ResponseEntity<?> removeRoleFromUser(@PathVariable("id") String id, @ModelAttribute("roleName") String roleName) throws CustomException {
        Long userId = null;
        try {
            userId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        boolean result = userService.removeRoleFromUser(userId, roleName);
        if (result) {
            String successMessage = "Đã xóa quyền khỏi tài khoản thành công!";
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            String errorMessage = "Xóa quyền khỏi tài khoản thất bại!";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
