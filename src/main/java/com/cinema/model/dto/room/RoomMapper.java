package com.cinema.model.dto.room;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.chair.ChairMapper;
import com.cinema.model.dto.room.repuest.RoomRequestDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import com.cinema.model.entity.*;
import com.cinema.repository.ChairRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.TheaterRepository;
import com.cinema.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    @Autowired
    private TheaterRepository theaterRepository ;

    @Autowired
    private MovieRepository movieRepository ;

    @Autowired
    private TimeSlotRepository timeSlotRepository ;

    @Autowired
    private ChairRepository chairRepository;

    @Autowired
    private ChairMapper chairMapper ;
    public RoomResponseDTO toRoomResponse(Room room) {
        List<Chair> list = chairRepository.findAllByRoomId(room.getId());
        return RoomResponseDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .numberOfSeats(room.getNumberOfSeats())
                .status(room.getStatus())
                .movieName(room.getMovie().getName())
                .theaterName(room.getTheater().getName())
                .timeSlotName(room.getTimeSlot().getName())
                .chairResponses(list.stream().map(item -> chairMapper.toChairResponse(item)).collect(Collectors.toList()))
                .build();
    }

    public Room toEntity(RoomRequestDTO roomRequest) throws CustomException {
        Movie movie = movieRepository.findById(roomRequest.getMovieId()).orElseThrow(()-> new CustomException("Movie Not Found")) ;
        Theater theater = theaterRepository.findById(roomRequest.getTheaterId()).orElseThrow(()-> new CustomException("Theater Not Found"));
        TimeSlot timeSlot = timeSlotRepository.findById(roomRequest.getTimeSlotId()).orElseThrow(()-> new CustomException("TimeSlot Not Found"));

        return Room.builder()
                .name(roomRequest.getName())
                .numberOfSeats(roomRequest.getNumberOfSeats())
                .status(true)
                .movie(movie)
                .theater(theater)
                .timeSlot(timeSlot)
                .build();
    }


}
