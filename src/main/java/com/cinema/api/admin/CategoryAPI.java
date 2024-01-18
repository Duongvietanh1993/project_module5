package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.category.request.CategoryRequestDTO;
import com.cinema.model.dto.category.response.CategoryResponseDTO;
import com.cinema.model.dto.user.response.UserResponseDTO;
import com.cinema.service.category.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/admin")
public class CategoryAPI {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryResponseDTO>> categoryAll(@RequestParam(name = "keyword") String keyword,
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
        Page<CategoryResponseDTO> categoryResponseDTOPage = categoryService.findAllCategory(keyword, pageable);
        return new ResponseEntity<>(categoryResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @ModelAttribute("category") CategoryRequestDTO categoryRequestDTO) throws CustomException {
        categoryService.save(categoryRequestDTO);
        String successMessage = "Bạn đã thêm thể loại phim thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id,
                                            @RequestPart("image") MultipartFile image,
                                            @ModelAttribute("category") CategoryRequestDTO categoryRequestDTO) throws CustomException {
        Long categoryId = null;
        try {
            categoryId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        categoryService.update(categoryId, image, categoryRequestDTO);
        String successMessage = "Đã sửa thông tin danh mục thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PatchMapping("/change-status-categories/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") String id) throws CustomException {
        Long categoryId = null;
        try {
            categoryId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        categoryService.changeStatusCategory(categoryId);
        String successMessage = "Bạn đã đổi trạng thái danh mục thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
