package com.cinema.repository;

import com.cinema.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Page<Movie> findAllByNameContainingIgnoreCase(String name , Pageable pageable) ;
    Boolean existsByName(String name) ;

}
