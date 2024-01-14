package com.cinema.repository;


import com.cinema.model.entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChairRepository extends JpaRepository<Chair, Long> {
    List<Chair> findAllByRoomId(Long idRoom) ;
    Boolean existsByName (String name) ;
}
