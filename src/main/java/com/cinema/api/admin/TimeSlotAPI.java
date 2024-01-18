package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.theater.response.TheaterResponseDTO;
import com.cinema.model.dto.timeSlot.repuest.TimeSlotRequestDTO;
import com.cinema.model.dto.timeSlot.response.TimeSlotResponseDTO;
import com.cinema.service.timeSlot.TimeSlotService;
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
public class TimeSlotAPI {
    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/time-slot")
    public ResponseEntity<Page<TimeSlotResponseDTO>> timeSlotAll(@RequestParam(defaultValue = "5", name = "limit") int limit,
                                                                 @RequestParam(defaultValue = "0", name = "page") int page,
                                                                 @RequestParam(defaultValue = "id", name = "sort") String sort,
                                                                 @RequestParam(defaultValue = "asc", name = "order") String order) {
        Pageable pageable;
        if (order.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<TimeSlotResponseDTO> timeSlotResponseDTOPage = timeSlotService.findAllTimeSlot(pageable);
        return new ResponseEntity<>(timeSlotResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping("/time-slot")
    public ResponseEntity<?> createTimeSlot(@Valid @ModelAttribute TimeSlotRequestDTO timeSlotRequestDTO) throws CustomException {
        timeSlotService.save(timeSlotRequestDTO);
        String successMessage = "Bạn đã thêm ca chiếu phim thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @PutMapping("/time-slot/{id}")
    public ResponseEntity<?> updateTimeSlot(@Valid @PathVariable("id") String id,
                                            @ModelAttribute TimeSlotRequestDTO timeSlotRequestDTO) throws CustomException {
        Long timeId = null;
        try {
            timeId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        timeSlotService.update(timeId, timeSlotRequestDTO);
        String successMessage = "Đã sửa thông tin ca chiếu phim thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PatchMapping("/change-status-time/{id}")
    public ResponseEntity<?> updateStatusTimeSlot(@PathVariable("id") String id) throws CustomException {
        Long timeId = null;
        try {
            timeId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        timeSlotService.changeStatusTimeSlot(timeId);
        String successMessage = "Bạn đã đổi trạng thái ca chiếu thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
