package com.cinema.service.timeSlot;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.timeSlot.repuest.TimeSlotRequestDTO;
import com.cinema.model.dto.timeSlot.response.TimeSlotResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TimeSlotService {
    Page<TimeSlotResponseDTO> findAllTimeSlot(Pageable pageable) ;

    TimeSlotResponseDTO findById(Long id) throws CustomException;

    TimeSlotResponseDTO save(TimeSlotRequestDTO timeSlotRequest) throws CustomException;

    TimeSlotResponseDTO update(Long id , TimeSlotRequestDTO timeSlotRequest) throws CustomException;

}
