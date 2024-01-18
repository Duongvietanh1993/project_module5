package com.cinema.service.chair;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.chair.repuest.ChairRequestDTO;
import com.cinema.model.dto.chair.response.ChairResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChairService {
    Page<ChairResponseDTO> findAllChair(String room, Pageable pageable);

    ChairResponseDTO findById(Long id) throws CustomException;

    ChairResponseDTO changeStatusChair(Long id) throws CustomException;

    ChairResponseDTO addChair(ChairRequestDTO chairRequestDTO) throws CustomException;
}
