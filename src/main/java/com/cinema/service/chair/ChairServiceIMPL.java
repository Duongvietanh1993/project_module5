package com.cinema.service.chair;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.chair.ChairMapper;
import com.cinema.model.dto.chair.repuest.ChairRequestDTO;
import com.cinema.model.dto.chair.response.ChairResponseDTO;
import com.cinema.model.entity.Chair;
import com.cinema.model.entity.Room;
import com.cinema.repository.ChairRepository;
import com.cinema.repository.RoomRepository;
import io.micrometer.common.util.StringUtils;
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
    @Autowired
    private RoomRepository roomRepository;
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

    @Override
    public ChairResponseDTO addChair(ChairRequestDTO chairRequestDTO) throws CustomException {
        // Kiểm tra xem tên ghế có được cung cấp không
        if (StringUtils.isBlank(chairRequestDTO.getName())) {
            throw new CustomException("Tên ghế không được để trống");
        }
        if (chairRepository.existsByName(chairRequestDTO.getName())) {
            throw new CustomException("Tên ghế đã bị trùng!");
        }

        // Kiểm tra xem roomId có được cung cấp không
        if (chairRequestDTO.getRoomId() == null) {
            throw new CustomException("ID phòng không được để trống");
        }

        // Tìm kiếm phòng dựa trên roomId
        Room room = roomRepository.findById(chairRequestDTO.getRoomId())
                .orElseThrow(() -> new CustomException("Không tìm thấy phòng với ID: " + chairRequestDTO.getRoomId()));

        // Tạo đối tượng Chair mới từ ChairRequestDTO
        Chair chair = chairMapper.toEntity(chairRequestDTO);

        // Thiết lập phòng cho ghế
        chair.setRoom(room);

        // Lưu ghế vào cơ sở dữ liệu
        Chair savedChair = chairRepository.save(chair);

        // Chuyển đổi ghế đã lưu thành ChairResponseDTO
        return chairMapper.toChairResponse(savedChair);
    }
}
