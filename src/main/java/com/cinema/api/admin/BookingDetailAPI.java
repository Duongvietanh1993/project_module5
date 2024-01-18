package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.bookingDetail.request.BookingDetailRequestDTO;
import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import com.cinema.model.entity.Movie;
import com.cinema.repository.MovieRepository;
import com.cinema.service.bookingDetail.BookingDetailService;
import com.cinema.service.movie.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/admin")
public class BookingDetailAPI {
    @Autowired
    private BookingDetailService bookingDetailService;
    @Autowired
    private MovieRepository movieRepository;


    @GetMapping("/booking")
    public ResponseEntity<Page<BookingDetailResponseDTO>> bookingAll(@RequestParam(name = "keyword") String keyword,
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
        Page<BookingDetailResponseDTO> bookingDetailResponseDTOPage = bookingDetailService.findAllBooking(keyword, pageable);
        return new ResponseEntity<>(bookingDetailResponseDTOPage, HttpStatus.OK);
    }

    @PatchMapping("/booking/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.changeStatusBookingDetail(id), HttpStatus.OK);
    }

    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) throws CustomException {
        bookingDetailService.cancelBookingDetail(id);
        String successMessage = "booking cancel success";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping("/tickets-sold")
    public ResponseEntity<?> getTicketsSoldByMovieAndMonth(
            @ModelAttribute("movieId") Long movieId,
            @ModelAttribute("month") int month,
            @ModelAttribute("year") int year
    ) throws CustomException {
        Integer ticketsSold = bookingDetailService.countTicketsSoldByMovieAndMonth(movieId, month, year);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new CustomException("Không tìm thấy phim có ID " + movieId));
        String successMessage = "Số lượng vé bán của phim " + movie.getName() + " trong tháng " + month + " năm " + year + " là: " + ticketsSold;

        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
