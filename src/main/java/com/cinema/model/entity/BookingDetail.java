package com.cinema.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BookingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean status ;
    private Double discount ;
    private Double subTotal ;
    private Double totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date bookingDate ;
    @ManyToOne
    @JoinColumn(name = "chair_id")
    private Chair chair ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
