package com.cinema.model.dto.theater;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.theater.repuest.TheaterRequestDTO;
import com.cinema.model.dto.theater.response.TheaterResponseDTO;
import com.cinema.model.entity.Location;
import com.cinema.model.entity.Theater;
import com.cinema.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TheaterMapper {

    @Autowired
    private LocationRepository locationRepository ;
    public TheaterResponseDTO toTheaterResponse(Theater theater) {
        return TheaterResponseDTO.builder()
                .id(theater.getId())
                .name(theater.getName())
                .locationName(theater.getLocation().getName())
                .build();
    }

    public Theater toEntity(TheaterRequestDTO theaterRequest) throws CustomException {
        Location location = locationRepository.findById(theaterRequest.getLocationId()).orElseThrow(() -> new CustomException("Location Not Found"));
        return Theater.builder()
                .name(theaterRequest.getName())
                .location(location)
                .build();
    }
}
