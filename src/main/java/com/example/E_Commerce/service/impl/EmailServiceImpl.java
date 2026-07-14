package com.example.E_Commerce.service.impl;

import com.example.E_Commerce.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOrderConfirmation(String to, String orderId) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);

            helper.setSubject("Order Placed Successfully | ShopSphere");

            String html = """
                    <!DOCTYPE html>
                    <html>
                    <body style="margin:0;padding:30px;background:#f5f5f5;font-family:Arial,sans-serif;">

                    <table align="center" width="600" cellpadding="0" cellspacing="0"
                           style="background:#ffffff;border-radius:10px;padding:30px;">

                        <tr>
                            <td align="center">

                                <h1 style="color:#06b6d4;">
                                    ShopSphere
                                </h1>

                                <h2 style="color:#222;">
                                    🎉 Order Placed Successfully
                                </h2>

                            </td>
                        </tr>

                        <tr>
                            <td>

                                <p>Hello,</p>

                                <p>
                                    Thank you for shopping with
                                    <b>ShopSphere</b>.
                                </p>

                                <p>
                                    Your order has been placed successfully.
                                </p>

                                <p>
                                    <b>Order ID :</b> #%s
                                </p>

                                <p>
                                    <b>Status :</b>
                                    <span style="color:green;">
                                        PENDING
                                    </span>
                                </p>

                                <p>
                                    You can check your order anytime from
                                    <b>My Orders</b>.
                                </p>

                                <br>

                                <a href="https://shopsphere-frontend.onrender.com/orders">
                                   style="background:#06b6d4;color:white;padding:12px 24px;text-decoration:none;border-radius:6px;">
                                    View My Orders
                                </a>

                                <br><br>

                                <p>
                                    Thank you for choosing ShopSphere ❤️
                                </p>

                                <p>
                                    Regards,<br>
                                    <b>Team ShopSphere</b>
                                </p>

                            </td>
                        </tr>

                    </table>

                    </body>
                    </html>
                    """.formatted(orderId);

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to send order confirmation email.", e);
        }
    }
}