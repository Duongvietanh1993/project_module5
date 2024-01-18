package com.cinema.model.dto.timeSlot;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.timeSlot.repuest.TimeSlotRequestDTO;
import com.cinema.model.dto.timeSlot.response.TimeSlotResponseDTO;
import com.cinema.model.entity.TimeSlot;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    public TimeSlotResponseDTO toTimeSlotMapper(TimeSlot timeSlot) {
        return TimeSlotResponseDTO.builder()
                .id(timeSlot.getId())
                .name(timeSlot.getName())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .status(true)
                .build();
    }

    public TimeSlot toEntity(TimeSlotRequestDTO timeSlotRequest) throws CustomException {
        return TimeSlot.builder()
                .name(timeSlotRequest.getName())
                .startTime(timeSlotRequest.getStartTime())
                .endTime(timeSlotRequest.getEndTime())
                .status(true)
                .build();
    }
}
