package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.category.response.CategoryResponseDTO;
import com.cinema.model.dto.movie.request.MovieRequestDTO;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.service.movie.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Validated
@RequestMapping("/api/v1/admin")
public class MovieAPI {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movie")
    public ResponseEntity<Page<MovieResponseDTO>> movieAll(@RequestParam(name = "keyword") String keyword,
                                                          @RequestParam(defaultValue = "5", name = "limit") int limit,
                                                          @RequestParam(defaultValue = "0", name = "page") int page,
                                                          @RequestParam(defaultValue = "id", name = "sort") String sort,
                                                          @RequestParam(defaultValue = "asc", name = "order") String order) {
        Pageable pageable;
        if (order.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<MovieResponseDTO> movieResponseDTOPage = movieService.findAllMovie(keyword, pageable);
        return new ResponseEntity<>(movieResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping("/movie")
    public ResponseEntity<?> createMovie(@Valid @ModelAttribute MovieRequestDTO movieRequestDTO) throws CustomException {
        movieService.save(movieRequestDTO);
        String successMessage = "Bạn đã thêm mới bộ phim thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }
    @PutMapping("/movie/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @ModelAttribute MovieRequestDTO movieRequestDTO) throws CustomException {
        MovieResponseDTO updatedMovie = movieService.update(id, movieRequestDTO);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }
    @PatchMapping("/change-status-movie/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id,@ModelAttribute("movieStatus") String newStatus) throws CustomException{
        movieService.changeStatusMovie(id,newStatus);
        String successMessage = "Bạn đã đổi trạng thái bộ phim thành công!";
        return new ResponseEntity<>(successMessage,HttpStatus.OK);
    }
}
