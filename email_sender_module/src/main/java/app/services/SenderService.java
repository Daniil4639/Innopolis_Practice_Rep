package app.services;

import app.model.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class SenderService {

    private final JavaMailSender sender;
    private final InternetAddress address;

    public SenderService(JavaMailSender sender) throws UnsupportedEncodingException {
        this.sender = sender;
        this.address = new InternetAddress("test_dan@local.com", "Test_Account");;
    }

    public void sendMessage(String subject, EmailMessage emailMessage) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(address);
        helper.setSubject(subject);
        helper.setTo(emailMessage.getEmail());
        helper.setText(emailMessage.getMessage());

        sender.send(message);
    }
}