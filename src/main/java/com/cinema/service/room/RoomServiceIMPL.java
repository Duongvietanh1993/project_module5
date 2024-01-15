package com.cinema.service.room;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.room.RoomMapper;
import com.cinema.model.dto.room.repuest.RoomRequestDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import com.cinema.model.entity.*;
import com.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RoomServiceIMPL implements RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Page<RoomResponseDTO> findAllRoom(String name, Pageable pageable) {
        Page<Room> roomPage;
        if (name.isEmpty() || name == null) {
            roomPage = roomRepository.findAll(pageable);
        } else {
            roomPage = roomRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return roomPage.map(item -> roomMapper.toRoomResponse(item));
    }

    @Override
    public RoomResponseDTO findById(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy phòng chiếu với ID: " + id));
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponseDTO save(RoomRequestDTO roomRequest) throws CustomException {
        if (roomRepository.existsByNameAndMovie_IdAndTimeSlot_Id(roomRequest.getName(), roomRequest.getMovieId(), roomRequest.getTimeSlotId())) {
            throw new CustomException("Phòng chiếu phim với cùng tên đã tồn tại");
        }

        Room room = roomRepository.save(roomMapper.toEntity(roomRequest));
        List<Chair> list = new ArrayList<>();
        String strings = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        int randomLocation = random.nextInt(strings.length());
        String name = String.valueOf(strings.charAt(randomLocation));
        for (int i = 0; i < roomRequest.getNumberOfSeats(); i++) {
            Chair chair = new Chair();
            chair.setName(name + "-" + (i + 1));
            chair.setStatus(false);
            chair.setRoom(room);
            list.add(chair);
        }
        chairRepository.saveAll(list);
        return roomMapper.toRoomResponse(room);
    }


    public RoomResponseDTO update(Long id, RoomRequestDTO roomRequestDTO) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy phòng chiếu với ID: " + id));
        Movie movie = movieRepository.findById(roomRequestDTO.getMovieId()).orElseThrow(() -> new CustomException("Không tìm thấy phim với ID: " + roomRequestDTO.getMovieId()));
        Theater theater = theaterRepository.findById(roomRequestDTO.getTheaterId()).orElseThrow(() -> new CustomException("Không tìm thấy rạp chiếu với ID: " + roomRequestDTO.getTheaterId()));
        TimeSlot timeSlot = timeSlotRepository.findById(roomRequestDTO.getTimeSlotId()).orElseThrow(() -> new CustomException("Không tìm thấy ca chiếu với ID: " + roomRequestDTO.getTimeSlotId()));

        room.setId(id);
        room.setName(roomRequestDTO.getName());
        room.getNumberOfSeats();
        room.setStatus(true);
        room.setMovie(movie);
        room.setTheater(theater);
        room.setTimeSlot(timeSlot);

        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponseDTO changeStatusRoom(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy phòng chiếu với ID: " + id));
        room.setStatus(!room.getStatus());
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }


}
