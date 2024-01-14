package com.cinema.api.client;


import com.cinema.exception.CustomException;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.service.chair.ChairService;
import com.cinema.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class HomeAPI {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ChairService chairService ;

    @GetMapping("/api/v1/movie")
    public ResponseEntity<Page<MovieResponseDTO>> userAll(@RequestParam(name = "keyword") String keyword,
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

    @GetMapping("/api/v1/chair")
    public ResponseEntity<?> chairAll() {
        return new ResponseEntity<>(chairService.findAllChair(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/chair/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomException {
        return new ResponseEntity<>(chairService.findById(id), HttpStatus.OK);
    }
}
