package com.cinema.model.dto.movie.response;


import com.cinema.model.entity.Categories;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cinema.model.entity.Movie;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseDTO {
    private String name;
    private String description;
    private String image;
    private String director;
    private String actors;
    private List<String> categoryName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    private Double duration;
    private Double price;
    private Double rating;
    private String movieStatus;

    public MovieResponseDTO(Movie movie) {
        this.name = movie.getName();
        this.description = movie.getDescription();
        this.image = movie.getImage();
        this.director = movie.getDirector();
        this.actors = movie.getActors();
        this.categoryName = movie.getCategories().stream().map(categories -> categories.getName()).collect(Collectors.toList());
        this.releaseDate = movie.getReleaseDate();
        this.duration = movie.getDuration();
        this.price = movie.getPrice();
        this.rating = movie.getRating();
        this.movieStatus = movie.getMovieStatus().name();
    }
}
