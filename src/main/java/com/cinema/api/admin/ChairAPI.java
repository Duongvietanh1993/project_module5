package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import com.cinema.model.dto.chair.repuest.ChairRequestDTO;
import com.cinema.model.dto.chair.response.ChairResponseDTO;
import com.cinema.service.chair.ChairService;
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
public class ChairAPI {
    @Autowired
    private ChairService chairService;

    @GetMapping("/chair")
    public ResponseEntity<Page<ChairResponseDTO>> chairAll(@RequestParam(name = "keyword") String keyword,
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
        Page<ChairResponseDTO> chairResponseDTOPage = chairService.findAllChair(keyword, pageable);
        return new ResponseEntity<>(chairResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping("/chair")
    public ResponseEntity<?> addChair(@ModelAttribute ChairRequestDTO chairRequestDTO) throws CustomException {
        chairService.addChair(chairRequestDTO);
        String successMessage = "Bạn đã thêm ghế thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);

    }

    @PatchMapping("/chair/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id) throws CustomException {
        Long chairId = null;
        try {
            chairId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("ID không hợp lệ", HttpStatus.BAD_REQUEST);
        }

        chairService.changeStatusChair(chairId);
        String successMessage = "Bạn đã đổi trạng thái ghế thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
