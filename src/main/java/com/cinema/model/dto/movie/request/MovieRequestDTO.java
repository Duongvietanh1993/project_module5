package com.cinema.model.dto.movie.request;

import com.cinema.model.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequestDTO {
    private String name;
    private String description;
    private MultipartFile image;
    private String director;
    private String actors;
    private Set<Categories> categories;
    private LocalDate releaseDate;
    private int duration;
    private String rated;
    private int isShowing;
}
