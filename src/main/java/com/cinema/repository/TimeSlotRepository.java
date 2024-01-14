package com.cinema.repository;

import com.cinema.model.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Boolean existsByName (String name) ;
    Boolean existsByStartTimeAndEndTime(LocalTime startTime , LocalTime endTime) ;
}
