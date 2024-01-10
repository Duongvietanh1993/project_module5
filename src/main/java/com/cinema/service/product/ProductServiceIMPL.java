package com.cinema.service.product;

import com.cinema.model.dto.product.request.ProductRequestDTO;
import com.cinema.model.dto.product.response.ProductResponseDTO;
import com.cinema.model.entity.Product;
import com.cinema.repository.ProductRepository;
import com.cinema.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceIMPL implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public List<ProductResponseDTO> getAll() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(ProductResponseDTO::new).toList();

    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        Product newProduct = new Product();
        newProduct.setName(productRequestDTO.getName());
        newProduct.setPrice(productRequestDTO.getPrice());
        //Upload file
        String fileName = uploadService.uploadImage(productRequestDTO.getImage());
        newProduct.setImage(fileName);
        //Lưu database
        productRepository.save(newProduct);
        //convert Product entity -> ProductResponseDTO
        return new ProductResponseDTO(newProduct);
    }
}
