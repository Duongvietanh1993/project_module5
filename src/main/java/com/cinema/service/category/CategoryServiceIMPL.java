package com.cinema.service.category;

import com.cinema.model.dto.category.request.CategoryRequestDTO;
import com.cinema.model.dto.category.response.CategoryResponseDTO;
import com.cinema.model.entity.Categories;
import com.cinema.repository.CategoryRepository;
import com.cinema.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceIMPL implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UploadService uploadService;
    @Override
    public List<CategoryResponseDTO> getAll() {
        List<Categories> categoriesList = categoryRepository.findAll();
        return categoriesList.stream().map(CategoryResponseDTO::new).toList();
    }

    @Override
    public CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) {
        Categories newCategory = new Categories();
        newCategory.setCategoryName(categoryRequestDTO.getCategoryName());
        newCategory.setStatus(categoryRequestDTO.isStatus());
        //upload file
        String fileName = uploadService.uploadImage(categoryRequestDTO.getImage());
        newCategory.setImage(fileName);
        //lưu DB
        categoryRepository.save(newCategory);
        return new CategoryResponseDTO(newCategory);
    }
}
