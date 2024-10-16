package com.os.notification_service.service.impl;

import com.os.notification_service.model.OrderNotification;
import com.os.notification_service.model.ProductNotification;
import com.os.notification_service.service.OrderNotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderNotificationServiceImpl implements OrderNotificationService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOrderCompletedEmail(OrderNotification notification) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(notification.getEmail());
            helper.setSubject("Siparişiniz Onaylandı...");
            StringBuilder productDetails = new StringBuilder();
            if (notification.getProducts() != null && !notification.getProducts().isEmpty()) {
                for (ProductNotification product : notification.getProducts()) {
                    productDetails.append("<div>")
                            .append("<strong>Ürün Adı:</strong> ").append(product.getName()).append("<br>")
                            .append("<strong>Açıklama:</strong> ").append(product.getDescription()).append("<br>")
                            .append("<strong>Fiyat:</strong> ").append(product.getPrice()).append(" TL<br>")
                            .append("<strong>Adet:</strong> ").append(product.getQuantity()).append("<br>")
                            .append("</div><br>");
                }
            } else {
                productDetails.append("Siparişinizde ürün bulunmamaktadır.");
            }
            String htmlContent = "<!DOCTYPE html>"
                    + "<html lang='en'>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "<title>Sipariş Onayı</title>"
                    + "<style>"
                    + ".email-container { font-family: Arial, sans-serif; margin: 0 auto; padding: 20px; width: 60%; border: 1px solid #ddd; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center; }"
                    + ".header { background-color: #f4f4f4; padding: 10px; font-size: 24px; font-weight: bold; }"
                    + ".content { margin: 20px 0; font-size: 16px; color: #555; text-align: left; }"
                    + ".footer { background-color: #f4f4f4; padding: 10px; font-size: 12px; color: #777; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='email-container'>"
                    + "<div class='header'>Sayın " + notification.getFirstName() + " " + notification.getLastName() + ",</div>"
                    + "<div class='content'>Siparişiniz başarıyla onaylanmıştır. Aşağıda sipariş detaylarınız bulunmaktadır:</div>"
                    + "<div class='content'>"
                    + productDetails.toString()
                    + "</div>"
                    + "<div class='content'><strong>Toplam Fiyat:</strong> " + notification.getTotalPrice() + " TL</div>"
                    + "<div class='footer'>© 2024 Eticaret. Tüm hakları saklıdır.</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            // E-posta içeriğini ayarla
            helper.setText(htmlContent, true);

            // E-postayı gönder
            mailSender.send(mimeMessage);

            System.out.println("E-posta başarıyla gönderildi: " + notification.getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("E-posta gönderirken bir hata oluştu: " + e.getMessage());
        }
    }
}
