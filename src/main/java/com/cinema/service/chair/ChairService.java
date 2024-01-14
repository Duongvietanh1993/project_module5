package com.cinema.service.chair;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.chair.response.ChairResponseDTO;

import java.util.List;

public interface ChairService {
    List<ChairResponseDTO> findAllChair() ;
    ChairResponseDTO findById (Long id) throws CustomException;

    ChairResponseDTO changeStatusChair(Long id ) throws CustomException;
}
