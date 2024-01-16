package com.cinema.service.email;

import com.cinema.model.dto.bookingDetail.BookingDetailMapper;
import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceIMPL implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public String sendEmail(String email, BookingDetailResponseDTO responseDTO) {
       try {
           SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
           simpleMailMessage.setFrom("jav230630@gmail.com");
           simpleMailMessage.setTo(email);
           simpleMailMessage.setSubject("Thông tin vé xem phim");

           // Tạo nội dung email
           String emailContent = createEmailContent(responseDTO);

           simpleMailMessage.setText(emailContent);

           javaMailSender.send(simpleMailMessage);

           return "Email sent successfully";
       }catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }
    private String createEmailContent(BookingDetailResponseDTO responseDTO) {
        StringBuilder emailContentBuilder = new StringBuilder();
        emailContentBuilder.append("Cảm ơn bạn đã đặt vé!!\n");
        emailContentBuilder.append("Chi tiết vé xem phim:\n");
        emailContentBuilder.append("Người đặt vé: ").append(responseDTO.getCustomer()).append("\n");
        emailContentBuilder.append("Tên phim: ").append(responseDTO.getMovieName()).append("\n");
        emailContentBuilder.append("Ngày đặt vé: ").append(responseDTO.getBookingDate()).append("\n");
        emailContentBuilder.append("Địa điểm: ").append(responseDTO.getTheaterName()).append(responseDTO.getLocationName()).append("\n");
        emailContentBuilder.append("Phòng chiếu: ").append(responseDTO.getRoomName()).append("\n");
        emailContentBuilder.append("Thời gian: ").append(responseDTO.getTimeSlotName()).append("\n");
        emailContentBuilder.append("Giá vé: ").append(responseDTO.getTotalAmount()).append("\n");
        return emailContentBuilder.toString();
    }
}
