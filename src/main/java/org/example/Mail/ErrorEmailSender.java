package org.example.Mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class ErrorEmailSender {
    public static void sendMail(String step, String error) {
        // Thông tin đăng nhập vào email của bạn
        String senderEmail = "huyhiep1907@gmail.com";
        String senderPassword = "vkofhuznagvorfan";

        // Thông tin người nhận email
        String recipientEmail = "hieppro221992@gmail.com";

        // Cấu hình properties cho JavaMail
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Thay đổi theo server email của bạn
        properties.put("mail.smtp.port", "587"); // Thay đổi theo cổng SMTP của bạn

        // Tạo đối tượng Session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            Message message = new MimeMessage(session);

            // Thiết lập người gửi và người nhận
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Thiết lập tiêu đề và nội dung email
            String subject = "Thông báo lỗi Data Warehouse";
            String content =  "Hiện tại đang có lỗi ở bước " + step + ". Lỗi đang gặp phải là: "  + error ;
            message.setSubject(subject);
            message.setText(content);

            // Gửi email
            Transport.send(message);

            System.out.println("Email đã được gửi thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Gọi phương thức sendMail với các tham số
        sendMail("Bước 1", "Lỗi không thể kết nối đến cơ sở dữ liệu");
    }
}
