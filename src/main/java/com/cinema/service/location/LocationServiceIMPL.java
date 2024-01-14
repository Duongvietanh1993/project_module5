package com.cinema.service.location;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.location.LocationMapper;
import com.cinema.model.dto.location.repuest.LocationRequestDTO;
import com.cinema.model.dto.location.response.LocationResponseDTO;
import com.cinema.model.entity.Location;
import com.cinema.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceIMPL implements LocationService {

    @Autowired
    private LocationRepository locationRepository ;

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public Page<LocationResponseDTO> findAllLocation(String name, Pageable pageable) {
        Page<Location> locationPage ;
        if (name.isEmpty() || name == null) {
            locationPage = locationRepository.findAll(pageable);
        } else  {
            locationPage = locationRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return locationPage.map(item -> locationMapper.toLocationResponse(item));
    }

    @Override
    public LocationResponseDTO findById(Long id) throws CustomException {
        Location location = locationRepository.findById(id).orElseThrow(()-> new CustomException("Không tìm thấy vị trí với ID: " + id));
        return locationMapper.toLocationResponse(location);
    }

    @Override
    public LocationResponseDTO save(LocationRequestDTO locationRequest) throws CustomException {
        if(locationRepository.existsByName(locationRequest.getName())) {
            throw new CustomException("Tên vị trí đã tồn tại!");
        }
        Location location = locationRepository.save(locationMapper.toEntity(locationRequest)) ;
        return locationMapper.toLocationResponse(location);
    }

    @Override
    public LocationResponseDTO update(Long id, LocationRequestDTO locationRequest) throws CustomException {
        Location location = locationRepository.findById(id).orElseThrow(()-> new CustomException("Không tìm thấy vị trí với ID: " + id)) ;

        location.setId(id);
        location.setName(locationRequest.getName());
        return locationMapper.toLocationResponse(locationRepository.save(location));
    }


}
