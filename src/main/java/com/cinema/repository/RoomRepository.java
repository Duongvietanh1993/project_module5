package com.cinema.repository;

import com.cinema.model.entity.Movie;
import com.cinema.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
   Page<Room> findAllByNameContainingIgnoreCase(String name , Pageable pageable) ;
   Boolean existsByTheater_Id(Long idTheater) ;
   Boolean existsByNameAndMovie_IdAndTimeSlot_Id(String nameRoom, Long idMovie, Long timeSlotId) ;
}
