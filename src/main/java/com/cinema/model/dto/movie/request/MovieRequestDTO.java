package com.cinema.model.dto.movie.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRequestDTO {
    @NotNull(message = "Không được để trống!")
    private String name;
    @NotNull(message = "Không được để trống!")
    private String description;
    private MultipartFile image;
    @NotNull(message = "Không được để trống!")
    private String director;
    @NotNull(message = "Không được để trống!")
    private String actors;
    private List<Long> categoryId;
    @NotNull(message = "Không được để trống!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date releaseDate;
    @NotNull(message = "Không được để trống!")
    private int duration;
    @NotNull(message = "Không được để trống!")
    private Double price;
    private String rating;
    @NotNull(message = "Không được để trống!")
    private String movieStatus;
}
