package com.cinema.service.category;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.category.request.CategoryRequestDTO;
import com.cinema.model.dto.category.response.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryResponseDTO> findAllCategory(String name , Pageable pageable);
    CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) throws CustomException;
    CategoryResponseDTO findById(Long id) throws CustomException;
    CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) throws CustomException;

}
