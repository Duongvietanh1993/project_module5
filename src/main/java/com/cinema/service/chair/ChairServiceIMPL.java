package com.cinema.service.chair;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.chair.ChairMapper;
import com.cinema.model.dto.chair.response.ChairResponseDTO;
import com.cinema.model.entity.Chair;
import com.cinema.repository.ChairRepository;
import com.cinema.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChairServiceIMPL implements ChairService{
    @Autowired
    private ChairRepository chairRepository ;
    @Autowired
    private ChairMapper chairMapper ;
    @Override
    public Page<ChairResponseDTO> findAllChair(String room, Pageable pageable) {
        Page<Chair> chairPage;
        if (room == null || room.isEmpty()) {
            chairPage = chairRepository.findAll(pageable);
        } else {
            chairPage = chairRepository.findByRoomNameContainingIgnoreCase(room, pageable);
        }
        return chairPage.map(item -> chairMapper.toChairResponse(item));
    }

    @Override
    public ChairResponseDTO findById(Long id) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy ghế với ID: " + id)) ;
        return chairMapper.toChairResponse(chair);
    }

    @Override
    public ChairResponseDTO changeStatusChair(Long id) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy ghế với ID: " + id)) ;
        chair.setStatus(!chair.getStatus());
        return chairMapper.toChairResponse(chairRepository.save(chair));
    }
}
