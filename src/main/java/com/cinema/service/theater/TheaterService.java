package com.cinema.service.theater;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.theater.repuest.TheaterRequestDTO;
import com.cinema.model.dto.theater.response.TheaterResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TheaterService {
    Page<TheaterResponseDTO> findAllTheater(String name , Pageable pageable) ;

    TheaterResponseDTO findById(Long id ) throws CustomException;

    TheaterResponseDTO save(TheaterRequestDTO theaterRequest) throws CustomException;

    TheaterResponseDTO update(Long id ,TheaterRequestDTO theaterRequest) throws CustomException;


}
