package com.cinema.service.category;

import com.cinema.model.dto.category.request.CategoryRequestDTO;
import com.cinema.model.dto.category.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getAll();
    CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO);
}
