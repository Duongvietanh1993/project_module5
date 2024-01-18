package com.cinema.service.bookingDetail;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.bookingDetail.request.BookingDetailRequestDTO;
import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.YearMonth;

public interface BookingDetailService {
    Page<BookingDetailResponseDTO> findAllBooking(String name , Pageable pageable); ;
    BookingDetailResponseDTO findById(Long id) throws CustomException;
    BookingDetailResponseDTO save(Authentication authentication, BookingDetailRequestDTO bookingDetailRequestDTO) throws CustomException;
    BookingDetailResponseDTO changeStatusBookingDetail(Long id) throws CustomException;
    void cancelBookingDetail(Long id) throws CustomException;
    Integer countTicketsSoldByMovieAndMonth(Long movieId, int month, int year);
}
