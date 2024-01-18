package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.location.repuest.LocationRequestDTO;
import com.cinema.model.dto.location.response.LocationResponseDTO;
import com.cinema.service.location.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class LocationAPI {
    @Autowired
    private LocationService locationServicel;

    @GetMapping("/location")
    public ResponseEntity<Page<LocationResponseDTO>> locationAll(@RequestParam(name = "keyword") String keyword,
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
        Page<LocationResponseDTO> movieResponseDTOPage = locationServicel.findAllLocation(keyword, pageable);
        return new ResponseEntity<>(movieResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<?> createLocation(@Valid @ModelAttribute LocationRequestDTO locationRequestDTO) throws CustomException {
        locationServicel.save(locationRequestDTO);
        String successMessage = "Bạn đã thêm mới địa điểm thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable("id") String id,
                                            @ModelAttribute LocationRequestDTO locationRequestDTO) throws CustomException {
        Long locationId = null;
        try {
            locationId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        locationServicel.update(locationId, locationRequestDTO);
        String successMessage = "Bạn đã thay đổi thông tin địa điểm thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
