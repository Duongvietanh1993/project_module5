package com.cinema.repository;

import com.cinema.model.entity.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories,Long> {
    Page<Categories> findAllByNameContainingIgnoreCase(String name, Pageable pageable) ;
    Boolean existsByName(String name) ;
    List<Categories> findAllById(Long id) ;
}
