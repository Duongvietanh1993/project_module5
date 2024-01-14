package com.cinema.model.dto.location;

import com.cinema.model.dto.location.repuest.LocationRequestDTO;
import com.cinema.model.dto.location.response.LocationResponseDTO;
import com.cinema.model.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationResponseDTO toLocationResponse(Location location) {
        return LocationResponseDTO.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }

    public Location toEntity(LocationRequestDTO locationRequest) {
        return Location.builder()
                .name(locationRequest.getName())
                .build();
    }
}
