package com.cinema.model.dto.chair;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.chair.repuest.ChairRequestDTO;
import com.cinema.model.dto.chair.response.ChairResponseDTO;
import com.cinema.model.entity.Chair;
import com.cinema.model.entity.Room;
import com.cinema.repository.RoomRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChairMapper {
    @Autowired
    private RoomRepository roomRepository ;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TheaterRepository theaterRepository ;
    public ChairResponseDTO toChairResponse(Chair chair) {
        return ChairResponseDTO.builder()
                .id(chair.getId())
                .name(chair.getName())
                .active(chair.getStatus())
                .roomName(chair.getRoom().getName())
                .timeSlotName(chair.getRoom().getTimeSlot().getName())
                .theaterName(chair.getRoom().getTheater().getName())
                .build();
    }

    public Chair toEntity(ChairRequestDTO chairRequest) throws CustomException {
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
        return Chair.builder()
                .name(chairRequest.getName())
                .status(false)
                .room(room)
                .build();
    }

}
