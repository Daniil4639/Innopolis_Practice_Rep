package app.controllers;

import app.model.EmailMessage;
import app.services.SenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send_email")
@RequiredArgsConstructor
public class EmailSenderController {

    private final SenderService service;

    @PostMapping("/registration")
    public void registrationMessage(@RequestBody EmailMessage emailMessage) throws MessagingException {
        service.sendMessage("Уведомление о регистрации", emailMessage);
    }

    @PostMapping("/update")
    public void updateMessage(@RequestBody EmailMessage emailMessage) throws MessagingException {
        service.sendMessage("Уведомление об обновлении данных", emailMessage);
    }

    @PostMapping("/grade")
    public void gradeMessage(@RequestBody EmailMessage emailMessage) throws MessagingException {
        service.sendMessage("Уведомление о записи на курс", emailMessage);
    }

    @PostMapping("/comment")
    public void commentMessage(@RequestBody EmailMessage emailMessage) throws MessagingException {
        service.sendMessage("Уведомление о публикации комментария", emailMessage);
    }
}