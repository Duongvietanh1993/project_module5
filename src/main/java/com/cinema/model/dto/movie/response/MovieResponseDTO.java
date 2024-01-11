package com.cinema.model.dto.movie.response;


import com.cinema.model.entity.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cinema.model.entity.Movie;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String image;
    private String director;
    private String actors;
    private Set<Categories> categories;
    private LocalDate releaseDate;
    private int duration;
    private String rated;
    private int isShowing;

    public MovieResponseDTO(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.description = movie.getDescription();
        this.image = movie.getImage();
        this.director = movie.getDirector();
        this.actors = movie.getActors();
        this.categories = movie.getCategories();
        this.releaseDate = movie.getReleaseDate();
        this.duration = movie.getDuration();
        this.rated = movie.getRated();
        this.isShowing = movie.getIsShowing();
    }
}
