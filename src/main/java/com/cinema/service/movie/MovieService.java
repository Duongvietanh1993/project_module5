package com.cinema.service.movie;

import com.cinema.model.dto.movie.request.MovieRequestDTO;
import com.cinema.model.dto.movie.response.MovieResponseDTO;


import java.util.List;

public interface MovieService {
    List<MovieResponseDTO> getAll();
    MovieResponseDTO save(MovieRequestDTO productRequestDTO);
}
