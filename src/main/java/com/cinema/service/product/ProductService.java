package com.cinema.service.product;

import com.cinema.model.dto.product.request.ProductRequestDTO;
import com.cinema.model.dto.product.response.ProductResponseDTO;


import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAll();
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);
}
