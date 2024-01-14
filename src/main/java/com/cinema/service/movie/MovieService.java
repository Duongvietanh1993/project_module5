package com.cinema.service.movie;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.movie.request.MovieRequestDTO;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface MovieService {
    Page<MovieResponseDTO> findAllMovie(String name , Pageable pageable) ;
    MovieResponseDTO save(MovieRequestDTO movieRequestDTO) throws CustomException;
    MovieResponseDTO update(Long id, MovieRequestDTO movieRequestDTO) throws CustomException;
    MovieResponseDTO changeStatusMovie(Long id,String newStatus) throws CustomException;
}
