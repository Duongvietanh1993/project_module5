package com.cinema.model.dto.bookingDetail;

import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import com.cinema.model.entity.BookingDetail;
import com.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookingDetailMapper {

    public BookingDetailResponseDTO toBookingDetailResponse(BookingDetail bookingDetail) {
        return BookingDetailResponseDTO.builder()
                .id(bookingDetail.getId())
                .name(bookingDetail.getName())
                .customer(bookingDetail.getUser().getUsername())
                .timeSlotName(bookingDetail.getChair().getRoom().getTimeSlot().getName())
                .roomName(bookingDetail.getChair().getRoom().getName())
                .theaterName(bookingDetail.getChair().getRoom().getTheater().getName())
                .locationName(bookingDetail.getChair().getRoom().getTheater().getLocation().getName())
                .movieName(bookingDetail.getChair().getRoom().getMovie().getName())
                .chairName(bookingDetail.getChair().getName())
                .discount(bookingDetail.getDiscount())
                .subTotal(bookingDetail.getSubTotal())
                .totalAmount(bookingDetail.getTotalAmount())
                .bookingDate(bookingDetail.getBookingDate())
                .status(bookingDetail.getStatus())
                .build();
    }
}
