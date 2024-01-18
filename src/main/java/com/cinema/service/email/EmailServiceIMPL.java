package com.cinema.service.email;

import com.cinema.model.dto.bookingDetail.response.BookingDetailResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


@Service
public class EmailServiceIMPL implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(String email, BookingDetailResponseDTO responseDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("jav230630@gmail.com");
            helper.setTo(email);
            helper.setSubject("Thông tin vé xem phim");

            // Tạo nội dung email
            String emailContent = createEmailContent(responseDTO);

            helper.setText(emailContent, true);

            javaMailSender.send(message);

            return "Email sent successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createEmailContent(BookingDetailResponseDTO responseDTO) {
        StringBuilder emailContentBuilder = new StringBuilder();
        emailContentBuilder.append("<html><body>");
        emailContentBuilder.append("<div style='padding: 20px;'>");
        emailContentBuilder.append("<h2>Cảm ơn bạn đã đặt vé!</h2>");
        emailContentBuilder.append("<p><strong>Người đặt vé:</strong> ").append(responseDTO.getCustomer()).append("</p>");
        emailContentBuilder.append("<p><strong>Tên phim:</strong> ").append(responseDTO.getMovieName()).append("</p>");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(responseDTO.getBookingDate());
        emailContentBuilder.append("<p><strong>Ngày đặt vé:</strong> ").append(formattedDate).append("</p>");
        emailContentBuilder.append("<p><strong>Địa điểm:</strong> ").append(responseDTO.getTheaterName()).append("</p>");
        emailContentBuilder.append("<p><strong>Phòng chiếu:</strong> ").append(responseDTO.getRoomName()).append("</p>");
        emailContentBuilder.append("<p><strong>Thời gian:</strong> ").append(responseDTO.getTimeSlotName()).append("</p>");
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedAmount = decimalFormat.format(responseDTO.getTotalAmount());
        emailContentBuilder.append("<p><strong>Giá vé:</strong> ").append(formattedAmount).append(" VND</p>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</body></html>");
        return emailContentBuilder.toString();
    }
}