package com.cinema.model.dto.movie.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Không được để trống!")
    private String name;
    @NotBlank(message = "Không được để trống!")
    private String description;
    private MultipartFile image;
    @NotBlank(message = "Không được để trống!")
    private String director;
    @NotBlank(message = "Không được để trống!")
    private String actors;
    private List<Long> categoryId;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    private Double duration;
    private Double price;
    private Double rating;
    @NotBlank(message = "Hãy chọn 1 trong các trạng thái (coming-showing-expired)!!")
    private String movieStatus;
}
