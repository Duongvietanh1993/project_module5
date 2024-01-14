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
    private String name;
    private String image;
    private Boolean status = true;

    public CategoryResponseDTO(Categories categories) {
        this.id = categories.getId();
        this.name = categories.getName();
        this.status = categories.getStatus();
        this.image = categories.getImage();
    }
}
