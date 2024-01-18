package com.cinema.service.movie;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.movie.request.MovieRequestDTO;
import com.cinema.model.dto.movie.response.MovieResponseDTO;
import com.cinema.model.entity.Categories;
import com.cinema.model.entity.Movie;
import com.cinema.model.entity.enums.MovieStatus;
import com.cinema.repository.CategoryRepository;
import com.cinema.repository.MovieRepository;
import com.cinema.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceIMPL implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Page<MovieResponseDTO> findAllMovie(String name, Pageable pageable) {
        Page<Movie> moviePage;
        if (name.isEmpty() || name == null) {
            moviePage = movieRepository.findAll(pageable);
        } else {
            moviePage = movieRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return moviePage.map(MovieResponseDTO::new);
    }

    @Override
    public MovieResponseDTO save(MovieRequestDTO movieRequestDTO) throws CustomException {
        if (movieRepository.existsByName(movieRequestDTO.getName())) {
            throw new CustomException("Tên phim đã tồn tại rồi!");
        }
        Movie newMovie = new Movie();
        newMovie.setName(movieRequestDTO.getName());
        newMovie.setDescription(movieRequestDTO.getDescription());
        //upload file
        String fileName = uploadService.uploadImage(movieRequestDTO.getImage());
        newMovie.setImage(fileName);

        newMovie.setDirector(movieRequestDTO.getDirector());
        newMovie.setActors(movieRequestDTO.getActors());
        //lấy categories
        List<Categories> categories = new ArrayList<>();
        List<Long> categoryIdList = movieRequestDTO.getCategoryId();
        if (categoryIdList != null) {
            categories = categoryIdList.stream()
                    .map(categoryId -> categoryRepository.findById(categoryId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        if (!categories.isEmpty()) {
            newMovie.setCategories(categories);
        }

        newMovie.setReleaseDate(movieRequestDTO.getReleaseDate());
        newMovie.setDuration(movieRequestDTO.getDuration());
        newMovie.setPrice(movieRequestDTO.getPrice());
        newMovie.setRating(movieRequestDTO.getRating());
        //thiết lập status
        String movieStatusString = movieRequestDTO.getMovieStatus();
        MovieStatus movieStatusEnum;
        switch (movieStatusString) {
            case "coming":
                movieStatusEnum = MovieStatus.COMING_SOON;
                break;
            case "showing":
                movieStatusEnum = MovieStatus.IS_SHOWING;
                break;
            case "expired":
                movieStatusEnum = MovieStatus.EXPIRED;
                break;
            default:
                throw new CustomException(movieStatusString + " not found");
        }
        newMovie.setMovieStatus(movieStatusEnum);

        //lứu lại và cast qua ResponseDTO
        Movie savedMovie = movieRepository.save(newMovie);
        return new MovieResponseDTO(savedMovie);
    }

    @Override
    public MovieResponseDTO update(Long id, MovieRequestDTO movieRequestDTO) throws CustomException {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new CustomException("Không tìm thấy phim với ID: " + id));

        // Kiểm tra và cập nhật các trường thông tin mới
        if (movieRequestDTO.getName() != null && !movieRequestDTO.getName().isEmpty()) {
            if (movieRepository.existsByName(movieRequestDTO.getName())) {
                throw new CustomException("Tên phim đã tồn tại rồi!");
            }
            existingMovie.setName(movieRequestDTO.getName());
        }

        if (movieRequestDTO.getDescription() != null) {
            existingMovie.setDescription(movieRequestDTO.getDescription());
        }

        if (movieRequestDTO.getImage() != null) {
            //upload file
            String fileName = uploadService.uploadImage(movieRequestDTO.getImage());
            existingMovie.setImage(fileName);
        }

        if (movieRequestDTO.getDirector() != null) {
            existingMovie.setDirector(movieRequestDTO.getDirector());
        }

        if (movieRequestDTO.getActors() != null) {
            existingMovie.setActors(movieRequestDTO.getActors());
        }

        if (movieRequestDTO.getCategoryId() != null) {
            List<Categories> categories = movieRequestDTO.getCategoryId().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            existingMovie.setCategories(categories);
        }

        if (movieRequestDTO.getReleaseDate() != null) {
            existingMovie.setReleaseDate(movieRequestDTO.getReleaseDate());
        }

        if (movieRequestDTO.getDuration() != 0) {
            existingMovie.setDuration(movieRequestDTO.getDuration());
        }

        if (movieRequestDTO.getPrice() != null) {
            existingMovie.setPrice(movieRequestDTO.getPrice());
        }

        if (movieRequestDTO.getRating() != null) {
            existingMovie.setRating(movieRequestDTO.getRating());
        }

        if (movieRequestDTO.getMovieStatus() != null) {
            String movieStatusString = movieRequestDTO.getMovieStatus();
            MovieStatus movieStatusEnum;
            switch (movieStatusString) {
                case "coming":
                    movieStatusEnum = MovieStatus.COMING_SOON;
                    break;
                case "showing":
                    movieStatusEnum = MovieStatus.IS_SHOWING;
                    break;
                case "expired":
                    movieStatusEnum = MovieStatus.EXPIRED;
                    break;
                default:
                    throw new CustomException(movieStatusString + " not found");
            }
            existingMovie.setMovieStatus(movieStatusEnum);
        }

        // Lưu lại và cast qua ResponseDTO
        Movie savedMovie = movieRepository.save(existingMovie);
        return new MovieResponseDTO(savedMovie);
    }

    @Override
    public MovieResponseDTO changeStatusMovie(Long id, String newStatus) throws CustomException {
        Movie movie = movieRepository.findById(id).orElseThrow(()-> new CustomException("Không tìm thấy phim với ID:" + id));

        switch (newStatus) {
            case "coming":
                movie.setMovieStatus(MovieStatus.COMING_SOON);
                break;
            case "showing" :
                movie.setMovieStatus(MovieStatus.IS_SHOWING);
                break;
            case "expired":
                movie.setMovieStatus(MovieStatus.EXPIRED);
                break;
            default:
                // Xử lý nếu trạng thái không hợp lệ
                throw new CustomException( newStatus + " Hãy chọn 1 trong các trạng thái (coming-showing-expired)!!");
        }

        return new MovieResponseDTO(movieRepository.save(movie));
    }
}
