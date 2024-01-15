package com.cinema.service.timeSlot;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.timeSlot.TimeSlotMapper;
import com.cinema.model.dto.timeSlot.repuest.TimeSlotRequestDTO;
import com.cinema.model.dto.timeSlot.response.TimeSlotResponseDTO;
import com.cinema.model.entity.TimeSlot;
import com.cinema.repository.RoomRepository;
import com.cinema.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TimeSlotServiceIMPL implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository ;
    @Autowired
    private TimeSlotMapper timeSlotMapper ;

    @Override
    public Page<TimeSlotResponseDTO> findAllTimeSlot(Pageable pageable) {
        Page<TimeSlot> timeSlots = timeSlotRepository.findAll(pageable) ;
        return timeSlots.map(item -> timeSlotMapper.toTimeSlotMapper(item));
    }

    @Override
    public TimeSlotResponseDTO findById(Long id) throws CustomException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(()-> new CustomException("Không tìm thấy ca chiếu phim!"));
        return timeSlotMapper.toTimeSlotMapper(timeSlot);
    }

    @Override
    public TimeSlotResponseDTO save(TimeSlotRequestDTO timeSlotRequest) throws CustomException {
        if (timeSlotRepository.existsByStartTimeAndEndTime(timeSlotRequest.getStartTime() , timeSlotRequest.getEndTime())) {
            throw new CustomException("Exits TimeSlot");
        }

        LocalTime startTime = timeSlotRequest.getStartTime();
        LocalTime endTime = timeSlotRequest.getEndTime();

        // Kiểm tra nếu startTime lớn hơn hoặc bằng endTime
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new CustomException("Giờ vào không được lớn hoặc bằng giờ kết thúc!");
        }

        TimeSlot timeSlot = timeSlotRepository.save(timeSlotMapper.toEntity(timeSlotRequest));
        return timeSlotMapper.toTimeSlotMapper(timeSlot);
    }

    @Override
    public TimeSlotResponseDTO update(Long id, TimeSlotRequestDTO timeSlotRequest) throws CustomException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy ca chiếu phim!"));


        LocalTime startTime = timeSlotRequest.getStartTime();
        LocalTime endTime = timeSlotRequest.getEndTime();

        // Kiểm tra nếu startTime lớn hơn hoặc bằng endTime
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new CustomException("Giờ vào và giờ kết thúc phải khác nhau!");
        }

        timeSlot.setId(id);
        timeSlot.setName(timeSlotRequest.getName());
        timeSlot.setStartTime(startTime); // Sửa thành startTime
        timeSlot.setEndTime(endTime); // Sửa thành endTime

        return timeSlotMapper.toTimeSlotMapper(timeSlotRepository.save(timeSlot));
    }



}
