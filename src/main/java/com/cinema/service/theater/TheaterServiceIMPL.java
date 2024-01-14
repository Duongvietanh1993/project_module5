package com.cinema.service.theater;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.theater.TheaterMapper;
import com.cinema.model.dto.theater.repuest.TheaterRequestDTO;
import com.cinema.model.dto.theater.response.TheaterResponseDTO;
import com.cinema.model.entity.Location;
import com.cinema.model.entity.Theater;
import com.cinema.repository.LocationRepository;
import com.cinema.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TheaterServiceIMPL implements TheaterService {

    @Autowired
    private TheaterRepository theaterRepository ;

    @Autowired
    private TheaterMapper theaterMapper ;

    @Autowired
    private LocationRepository locationRepository ;

    @Override
    public Page<TheaterResponseDTO> findAllTheater(String name, Pageable pageable) {
        Page<Theater> theaterPage ;
        if (name == null || name.isEmpty()) {
            theaterPage = theaterRepository.findAll(pageable) ;
        } else {
            theaterPage = theaterRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return theaterPage.map(item -> theaterMapper.toTheaterResponse(item));
    }

    @Override
    public TheaterResponseDTO findById(Long id) throws CustomException {
        Theater theater = theaterRepository.findById(id).orElseThrow(()-> new CustomException("Không tìm thấy rạp chiếu với ID: " + id));
        return theaterMapper.toTheaterResponse(theater);
    }

    @Override
    public TheaterResponseDTO save(TheaterRequestDTO theaterRequest) throws CustomException {
        if (theaterRepository.existsByName(theaterRequest.getName())){
            throw  new CustomException("Exits TheaterName") ;
        }
        Theater theater = theaterRepository.save(theaterMapper.toEntity(theaterRequest));
        return theaterMapper.toTheaterResponse(theater);
    }

    @Override
    public TheaterResponseDTO update(Long id, TheaterRequestDTO theaterRequest) throws CustomException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy rạp chiếu với ID: " + id));

        Location location = locationRepository.findById(theaterRequest.getLocationId()).orElseThrow(()-> new CustomException("Không tìm thấy vị trí với ID: " + id));
        theater.setId(id);
        theater.setName(theaterRequest.getName());
        theater.setLocation(location);
        return theaterMapper.toTheaterResponse(theaterRepository.save(theater));
    }


}
