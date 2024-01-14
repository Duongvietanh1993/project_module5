package com.cinema.service.location;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.location.repuest.LocationRequestDTO;
import com.cinema.model.dto.location.response.LocationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {
    Page<LocationResponseDTO> findAllLocation(String name, Pageable pageable) ;
    LocationResponseDTO findById(Long id) throws CustomException;

    LocationResponseDTO save (LocationRequestDTO locationRequest) throws CustomException;

    LocationResponseDTO update(Long id , LocationRequestDTO locationRequest) throws CustomException;


}
