package com.cinema.repository;

import com.cinema.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findAllByNameContainingIgnoreCase(String name, Pageable pageable) ;
    Boolean existsByName (String name) ;
}
