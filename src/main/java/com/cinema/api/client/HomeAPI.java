package com.cinema.api.client;


import com.cinema.exception.CustomException;
import com.cinema.model.dto.bookingDetail.request.BookingDetailRequestDTO;
import com.cinema.model.dto.chair.response.ChairResponseDTO;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import com.cinema.service.bookingDetail.BookingDetailService;
import com.cinema.service.chair.ChairService;
import com.cinema.service.movie.MovieService;
import com.cinema.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class HomeAPI {
    @Autowired
    private MovieService movieService;
    @Autowired
    private ChairService chairService;
    @Autowired
    private BookingDetailService bookingDetailService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/api/v1/movie")
    public ResponseEntity<Page<MovieResponseDTO>> userAll(@RequestParam(name = "keyword") String keyword,
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
        Page<MovieResponseDTO> movieResponseDTOPage = movieService.findAllMovie(keyword, pageable);
        return new ResponseEntity<>(movieResponseDTOPage, HttpStatus.OK);
    }

    @GetMapping("/api/v1/chair")
    public ResponseEntity<Page<ChairResponseDTO>> chairAll(@RequestParam(name = "keyword") String keyword,
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
        Page<ChairResponseDTO> chairResponseDTOPage = chairService.findAllChair(keyword, pageable);
        return new ResponseEntity<>(chairResponseDTOPage, HttpStatus.OK);
    }

    @GetMapping("/api/v1/chair/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(chairService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/api/v1/booking")
    public ResponseEntity<?> createBooking(Authentication authentication, @ModelAttribute BookingDetailRequestDTO bookingDetailRequestDTO) throws CustomException {
        bookingDetailService.save(authentication, bookingDetailRequestDTO);
        String successMessage = "Bạn đã đặt vé thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }
    @GetMapping("/api/v1/rooms")
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
}
