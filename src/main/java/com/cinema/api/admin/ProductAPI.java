package com.cinema.api.admin;

import com.cinema.model.dto.product.request.ProductRequestDTO;
import com.cinema.model.dto.product.response.ProductResponseDTO;
import com.cinema.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class ProductAPI {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<?> save(@ModelAttribute("product") ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.save(productRequestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }
}
