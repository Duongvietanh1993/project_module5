package com.cinema.model.dto.category.response;


import com.cinema.model.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String categoryName;
    private String image;
    private boolean status = true;

    public CategoryResponseDTO(Categories categories) {
        this.id = categories.getId();
        this.categoryName = categories.getCategoryName();
        this.status = categories.isStatus();
        this.image = categories.getImage();
    }
}
