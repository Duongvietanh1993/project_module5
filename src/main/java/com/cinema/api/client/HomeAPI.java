package com.cinema.api.client;


import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class HomeAPI {
    @Autowired
    private MovieService productService;
    @GetMapping("/api/v1/cinema")
    public ResponseEntity<?> getAll(){
        List<MovieResponseDTO> list=productService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
