package com.cinema.repository;


import com.cinema.model.entity.Chair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChairRepository extends JpaRepository<Chair, Long> {
    List<Chair> findAllByRoomId(Long idRoom) ;
    Boolean existsByName (String name) ;
    List<Chair> findByRoomId(Long roomId);
    @Query("SELECT c FROM Chair c WHERE LOWER(c.room.name) LIKE LOWER(CONCAT('%', :roomName, '%'))")
    Page<Chair> findByRoomNameContainingIgnoreCase(String roomName, Pageable pageable);
}
