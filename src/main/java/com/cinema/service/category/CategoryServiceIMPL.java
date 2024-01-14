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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
    public CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) throws CustomException {
        //kiểm tra trùng lặp tên danh mục
        if (categoryRepository.existsByName(categoryRequestDTO.getName())){
            throw new CustomException("Tên danh mục đã được sử dụng!");
        }
        Categories newCategory = new Categories();
        newCategory.setName(categoryRequestDTO.getName());
        newCategory.setStatus(categoryRequestDTO.getStatus());
        //upload file
        String fileName = uploadService.uploadImage(categoryRequestDTO.getImage());
        newCategory.setImage(fileName);
        //lưu DB
        categoryRepository.save(newCategory);
        return new CategoryResponseDTO(newCategory);
    }

    @Override
    public CategoryResponseDTO update(Long id, @RequestPart("image") MultipartFile image, CategoryRequestDTO categoryRequestDTO) throws CustomException {
        Categories categories = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy danh mục với ID: " + id));

        //kiểm tra trùng lặp tên danh mục
        if (categoryRepository.existsByName(categoryRequestDTO.getName())){
            throw new CustomException("Tên danh mục đã tồn tại rồi!");
        }

        categories.setId(id);
        categories.setName(categoryRequestDTO.getName());

        // Kiểm tra xem có hình ảnh mới được tải lên không
        if (image != null) {
            // Upload hình ảnh mới
            String fileName = uploadService.uploadImage(image);
            categories.setImage(fileName);
        }

        categoryRepository.save(categories);
        return new CategoryResponseDTO(categories);
    }

    @Override
    public Boolean changeStatusCategory(Long id) throws CustomException {
        Categories categories = categoryRepository.findById(id).orElseThrow(()->new CustomException("Không tìm thấy danh mục với ID: " + id));
        categories.setStatus(!categories.getStatus());
        categoryRepository.save(categories);
        return true;
    }
}
