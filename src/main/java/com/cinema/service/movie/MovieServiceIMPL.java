package com.cinema.service.movie;

import com.cinema.model.dto.movie.request.MovieRequestDTO;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.model.entity.Movie;
import com.cinema.repository.MovieRepository;
import com.cinema.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceIMPL implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public List<MovieResponseDTO> getAll() {
        List<Movie> movieList = movieRepository.findAll();
        return movieList.stream().map(MovieResponseDTO::new).toList();

    }

    @Override
    public MovieResponseDTO save(MovieRequestDTO movieRequestDTO) {
        Movie newMovie = new Movie();
        newMovie.setName(movieRequestDTO.getName());
        newMovie.setDescription(movieRequestDTO.getDescription());

        //Upload file
        String fileName = uploadService.uploadImage(movieRequestDTO.getImage());
        newMovie.setImage(fileName);
        //Lưu database
        movieRepository.save(newMovie);
        //convert Movie entity -> MovieResponseDTO
        return new MovieResponseDTO(newMovie);
    }
}
