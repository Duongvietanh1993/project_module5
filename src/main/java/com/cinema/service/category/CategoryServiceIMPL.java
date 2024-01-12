package com.cinema.service.category;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.category.request.CategoryRequestDTO;
import com.cinema.model.dto.category.response.CategoryResponseDTO;
import com.cinema.model.entity.Categories;
import com.cinema.repository.CategoryRepository;
import com.cinema.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceIMPL implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UploadService uploadService;


    @Override
    public Page<CategoryResponseDTO> findAllCategory(String name, Pageable pageable) {
        Page<Categories> categoriesPage;
        if (name == null || name.isEmpty()){
            categoriesPage = categoryRepository.findAll(pageable);
        }else {
            categoriesPage = categoryRepository.findAllByNameContainingIgnoreCase(name,pageable);
        }
        return categoriesPage.map(CategoryResponseDTO::new);
    }

    @Override
    public CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) {
        Categories newCategory = new Categories();
        newCategory.setCategoryName(categoryRequestDTO.getCategoryName());
        newCategory.setStatus(categoryRequestDTO.getStatus());
        //upload file
        String fileName = uploadService.uploadImage(categoryRequestDTO.getImage());
        newCategory.setImage(fileName);
        //lưu DB
        categoryRepository.save(newCategory);
        return new CategoryResponseDTO(newCategory);
    }

    @Override
    public CategoryResponseDTO findById(Long id) throws CustomException {
        Categories categories = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy danh mục!"));
        return new CategoryResponseDTO(categories);
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) throws CustomException {
        Categories categories = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy danh mục!"));

        categories.setId(id);
        categories.setCategoryName(categoryRequestDTO.getCategoryName());
        return new CategoryResponseDTO(categories);
    }
}
