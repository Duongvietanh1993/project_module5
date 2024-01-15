package com.cinema.model.dto.bookingDetail;

import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import com.cinema.model.entity.BookingDetail;
import com.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingDetailMapper {
    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private TimeSlotRepository timeSlotRepository ;

    @Autowired
    private RoomRepository roomRepository ;

    @Autowired
    private TheaterRepository theaterRepository ;
    @Autowired
    private LocationRepository locationRepository ;

    @Autowired
    private MovieRepository movieRepository ;

    @Autowired
    private ChairRepository chairRepository ;
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
