package com.cinema.api.admin;

import com.cinema.exception.CustomException;
import com.cinema.service.chair.ChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class ChairAPI {
    @Autowired
    private ChairService chairService ;
    @GetMapping("/chair")
    public ResponseEntity<?> chairAll() {
        return new ResponseEntity<>(chairService.findAllChair(), HttpStatus.OK);
    }
    @PatchMapping("/chair/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id)throws CustomException {
        chairService.changeStatusChair(id);
        String successMessage = "Bạn đã đổi trạng thái ghế thành công!";
        return new ResponseEntity<>(successMessage,HttpStatus.OK);
    }
}
