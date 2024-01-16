package com.cinema.service.email;

import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;

public interface EmailService {
    String sendEmail(String email, BookingDetailResponseDTO responseDTO);
}
