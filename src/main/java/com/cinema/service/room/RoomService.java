package com.cinema.service.room;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.room.repuest.RoomRequestDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    Page<RoomResponseDTO> findAllRoom(String name,Pageable pageable) ;
    RoomResponseDTO findById(Long id) throws CustomException;
    RoomResponseDTO save(RoomRequestDTO roomRequest) throws CustomException;
    RoomResponseDTO update(Long id , RoomRequestDTO roomRequest) throws CustomException;
    RoomResponseDTO changeStatusRoom(Long id) throws CustomException;
    Boolean changeStatusTimeSlot(Long id) throws CustomException;

}
