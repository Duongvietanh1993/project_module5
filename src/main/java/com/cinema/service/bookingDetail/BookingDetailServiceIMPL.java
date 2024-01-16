package com.cinema.service.bookingDetail;

import com.cinema.exception.CustomException;
import com.cinema.model.dto.bookingDetail.BookingDetailMapper;
import com.cinema.model.dto.bookingDetail.request.BookingDetailRequestDTO;
import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import com.cinema.model.entity.*;
import com.cinema.model.entity.enums.MemberLevel;
import com.cinema.repository.*;
import com.cinema.security.user_principle.UserPrinciple;
import com.cinema.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingDetailServiceIMPL implements BookingDetailService {
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ChairRepository chairRepository;
    @Autowired
    private BookingDetailMapper bookingDetailMapper;
    @Autowired
    private EmailService emailService;


    @Override
    public Page<BookingDetailResponseDTO> findAllBooking(String name, Pageable pageable) {
        Page<BookingDetail> bookingDetailPage;
        if (name == null || name.isEmpty()) {
            bookingDetailPage = bookingDetailRepository.findAll(pageable);
        } else {
            bookingDetailPage = bookingDetailRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return bookingDetailPage.map(item -> bookingDetailMapper.toBookingDetailResponse(item));
    }

    @Override
    public BookingDetailResponseDTO findById(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("BookingDetail Not Found"));
        return bookingDetailMapper.toBookingDetailResponse(bookingDetail);
    }

    @Override
    public BookingDetailResponseDTO save(Authentication authentication, BookingDetailRequestDTO bookingDetailRequestDTO) throws CustomException {
        // Lấy thông tin người dùng từ đối tượng xác thực (authentication)
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userRepository.findById(userPrinciple.getUser().getId())
                .orElseThrow(() -> new CustomException("Không tìm thấy người dùng có ID" + userPrinciple.getUser().getId()));

        // Lấy thông tin phim từ bookingDetailRequestDTO
        Movie movie = movieRepository.findById(bookingDetailRequestDTO.getMovieId())
                .orElseThrow(() -> new CustomException("Không tìm thấy phim có ID" + bookingDetailRequestDTO.getMovieId()));

        // Lấy thông tin phòng chiếu từ bookingDetailRequestDTO
        Room room = roomRepository.findById(bookingDetailRequestDTO.getRoomId())
                .orElseThrow(() -> new CustomException("Không tìm thấy phòng chiếu phim có ID" + bookingDetailRequestDTO.getRoomId()));
        // Kiểm tra phòng chiếu có phim đó không
        if (!room.getMovie().equals(movie)) {
            throw new CustomException("Phòng chiếu này không có phim " + movie.getName());
        }

        // Lấy thông tin rạp chiếu phim từ bookingDetailRequestDTO
        Theater theater = theaterRepository.findById(bookingDetailRequestDTO.getTheaterId())
                .orElseThrow(() -> new CustomException("Không tìm thấy rạp chiếu phim có ID" + bookingDetailRequestDTO.getTheaterId()));
        // kiểm tra rạp chiếu có phòng chiếu này không
        if (!room.getTheater().equals(theater)) {
            throw new CustomException("Rạp chiếu này không có phòng chiếu " + room.getName());
        }

        // Lấy thông tin ghế từ bookingDetailRequestDTO
        Chair chair = chairRepository.findById(bookingDetailRequestDTO.getChaiId())
                .orElseThrow(() -> new CustomException("Không tìm thấy ghế có ID" + bookingDetailRequestDTO.getChaiId()));
        // Kiểm tra ghế có thuộc phòng chiếu không
        if (!room.getChairs().contains(chair)) {
            throw new CustomException("Ghế không thuộc phòng chiếu " + chair.getName());
        }
        // Kiểm tra ghế đã được đặt chưa
        if (chair.getStatus()) {
            throw new CustomException("Ghế đã được đặt");
        }


        // Lấy thông tin ca chiếu phim từ bookingDetailRequestDTO
        TimeSlot timeSlot = timeSlotRepository.findById(bookingDetailRequestDTO.getTimeSlotId())
                .orElseThrow(() -> new CustomException("Không tìm thấy ca chiếu phim có ID" + bookingDetailRequestDTO.getTimeSlotId()));
        if (room.getTimeSlot() != timeSlot) {
            throw new CustomException("Phim không có ca chiếu này!");
        }

        // Đếm số lượng BookingDetail đã được xác nhận (status = true)
        Integer count = bookingDetailRepository.countByStatus(true);

        // Cập nhật MemberLevel và điểm tích lũy của người dùng dựa trên số lượng BookingDetail đã xác nhận
        if (count > 0 && count <= 20) {
            user.setMemberLevel(MemberLevel.BRONZE);
        } else if (count > 20 && count <= 40) {
            user.setMemberLevel(MemberLevel.SILVER);
        } else if (count > 40 && count <= 60) {
            user.setMemberLevel(MemberLevel.GOLD);
        } else if (count > 60) {
            user.setMemberLevel(MemberLevel.PLATINUM);
        }
        user.setScorePoints(count);

        // Tính toán giảm giá và tổng số tiền dựa trên MemberLevel
        Double discount = 0.0;
        Double subTotal = 0.0;
        Double totalAmount = 0.0;
        switch (user.getMemberLevel().name()) {
            case "BRONZE":
                discount = (0.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount;
                break;
            case "SILVER":
                discount = (3.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount;
                break;
            case "GOLD":
                discount = (5.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount;
                break;
            case "PLATINUM":
                discount = (10.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount;
                break;
        }
        // Tạo một đối tượng BookingDetail mới
        BookingDetail bookingDetail = new BookingDetail();
        // Đặt thông tin người dùng
        bookingDetail.setUser(user);
        // Đặt thông tin ghế
        bookingDetail.setChair(chair);
        // Đặt thông tin giảm giá
        bookingDetail.setDiscount(discount);
        // Đặt thông tin tổng tiền hàng
        bookingDetail.setSubTotal(subTotal);
        // Đặt tổng số tiền cần thanh toán
        bookingDetail.setTotalAmount(totalAmount);
        // Đặt trạng thái của ghế là đã đặt
        chair.setStatus(true);
        // Đặt ngày đặt vé là ngày hiện tại
        bookingDetail.setBookingDate(new Date());
        // Đặt trạng thái của đặt vé là đã thanh toán
        bookingDetail.setStatus(true);

        // Tạo tên phòng chiếu kết hợp với một số tự tăng không bị trùng lặp
        String roomName = room.getName();
        Long uniqueId = bookingDetailRepository.count(); // Số tự tăng không bị trùng lặp
        String combinedName = roomName + "-" + uniqueId;
        bookingDetail.setName(combinedName); // Gán tên phòng chiếu đã tạo cho bookingDetail

        // Lưu đối tượng BookingDetail vào cơ sở dữ liệu
        BookingDetail createBookingDetail = bookingDetailRepository.save(bookingDetail);

        // Gửi email thông báo mua vé thành công
        emailService.sendEmail(user.getEmail(), bookingDetailMapper.toBookingDetailResponse(createBookingDetail));

        // Trả về một đối tượng BookingDetailResponseDTO chứa thông tin chi tiết của BookingDetail vừa được tạo
        return bookingDetailMapper.toBookingDetailResponse(createBookingDetail);
    }

    @Override
    public BookingDetailResponseDTO changeStatusBookingDetail(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy chi tiết đặt chỗ!"));

        if (!bookingDetail.getStatus()) {
            bookingDetail.setStatus(true);
        }
        bookingDetailRepository.save(bookingDetail);
        return bookingDetailMapper.toBookingDetailResponse(bookingDetail);
    }

    @Override
    public void cancelBookingDetail(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy chi tiết đặt chỗ!"));
        if (!bookingDetail.getStatus()) {
            roomRepository.deleteById(id);
        }
    }
}
