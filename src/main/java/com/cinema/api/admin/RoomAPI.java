package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.model.dto.room.repuest.RoomRequestDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import com.cinema.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class RoomAPI {
    @Autowired
    private RoomService roomService;
    @GetMapping("/rooms")
    public ResponseEntity<Page<RoomResponseDTO>> UserAll(@RequestParam(name = "keyword") String keyword,
                                                          @RequestParam(defaultValue = "5", name = "limit") int limit,
                                                          @RequestParam(defaultValue = "0", name = "page") int page,
                                                          @RequestParam(defaultValue = "id", name = "sort") String sort,
                                                          @RequestParam(defaultValue = "asc", name = "order") String order) {
        Pageable pageable;
        if (order.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<RoomResponseDTO> movieResponseDTOPage = roomService.findAllRoom(keyword, pageable);
        return new ResponseEntity<>(movieResponseDTOPage, HttpStatus.OK);
    }
    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequestDTO roomRequestDTO)throws CustomException{
        roomService.save(roomRequestDTO);
        String successMessage = "Bạn đã thêm phòng chiếu phim thành công!";
        return new ResponseEntity<>(successMessage,HttpStatus.CREATED);
    }
    @PutMapping("/rooms/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id,
                                        @RequestBody RoomRequestDTO roomRequestDTO)throws CustomException{
        return new ResponseEntity<>(roomService.update(id, roomRequestDTO), HttpStatus.OK);
    }
    @PatchMapping("/change-status-room/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id) throws CustomException {
        roomService.changeStatusRoom(id);
        String successMessage = "Bạn đã đổi trạng thái phòng chiếu thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
