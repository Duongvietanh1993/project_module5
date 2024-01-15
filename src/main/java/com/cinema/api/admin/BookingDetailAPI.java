package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.bookingDetail.request.BookingDetailRequestDTO;
import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import com.cinema.model.dto.room.response.RoomResponseDTO;
import com.cinema.service.bookingDetail.BookingDetailService;
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

@RestController
@RequestMapping("/api/v1/admin")
public class BookingDetailAPI {
    @Autowired
    private BookingDetailService bookingDetailService;

    @GetMapping("/booking")
    public ResponseEntity<Page<BookingDetailResponseDTO>> UserAll(@RequestParam(name = "keyword") String keyword,
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
        String successMessage = "booking cancel success" ;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
