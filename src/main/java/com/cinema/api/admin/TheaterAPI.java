package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.theater.repuest.TheaterRequestDTO;
import com.cinema.model.dto.theater.response.TheaterResponseDTO;
import com.cinema.service.theater.TheaterService;
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
public class TheaterAPI {
    @Autowired
    private TheaterService theaterService;

    @GetMapping("/theater")
    public ResponseEntity<Page<TheaterResponseDTO>> theaterAll(@RequestParam(name = "keyword") String keyword,
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
        Page<TheaterResponseDTO> theaterResponseDTOPage = theaterService.findAllTheater(keyword, pageable);
        return new ResponseEntity<>(theaterResponseDTOPage, HttpStatus.OK);
    }

    @PostMapping("/theater")
    public ResponseEntity<?> createTheater(@Valid @ModelAttribute TheaterRequestDTO theaterRequestDTO) throws CustomException {
        theaterService.save(theaterRequestDTO);
        String successMessage = "Bạn đã thêm rạp chiếu phim thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @PutMapping("/theater/{id}")
    public ResponseEntity<?> createTheater(@Valid @PathVariable("id") Long id,
                                           @ModelAttribute TheaterRequestDTO theaterRequestDTO) throws CustomException {
        theaterService.update(id,theaterRequestDTO);
        String successMessage = "Bạn đã sửa thông tin rạp chiếu phim thành công!";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
