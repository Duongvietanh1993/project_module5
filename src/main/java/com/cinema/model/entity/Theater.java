package com.cinema.model.entity;

import com.cinema.model.entity.Location;
import com.cinema.model.entity.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Theater {
    // rạp chiếu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    @ManyToOne
    private Location location ;

    @OneToMany(mappedBy = "theater")
    private Set<Room> rooms ;

}
