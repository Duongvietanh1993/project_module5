package com.cinema.controller;


import com.cinema.model.dto.product.response.ProductResponseDTO;
import com.cinema.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class HomeController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<?> getAll(){
        List<ProductResponseDTO> list=productService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
