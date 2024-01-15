package com.cinema.repository;

import com.cinema.model.entity.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    Page<Theater> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    Boolean existsByName (String name) ;
}
