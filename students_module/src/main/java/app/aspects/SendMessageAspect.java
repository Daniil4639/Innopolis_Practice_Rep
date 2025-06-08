package app.aspects;

import app.clients.GradeClient;
import app.models.EmailMessage;
import app.models.Grade;
import app.models.Student;
import app.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Aspect
@Component
@RequiredArgsConstructor
public class SendMessageAspect {

    private final WebClient webClient;
    private final StudentService service;
    private final GradeClient gradeClient;

    @Pointcut("@annotation(SendAfterRegistration)")
    public void callAfterStudentCreate() {}

    @Pointcut("@annotation(SendAfterUpdate)")
    public void callAfterStudentUpdate() {}

    @Pointcut("@annotation(SendAfterAddGrade)")
    public void callAfterAddGrade() {}

    @Pointcut("@annotation(SendAfterComment)")
    public void callAfterCreateComment() {}

    @AfterReturning("callAfterStudentCreate()")
    public void sendAfterStudentCreate(JoinPoint point) {
        Student student = (Student) point.getArgs()[0];

        String message = String.format(REGISTRATION_MESSAGE, student.getFullName());

        EmailMessage emailMessage = new EmailMessage(student.getEmail(), message);

        webClient.post()
                .uri("registration")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(emailMessage)
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }

    @AfterReturning("callAfterStudentUpdate()")
    public void sendAfterStudentUpdate(JoinPoint point) {
        Integer studentId = (Integer) point.getArgs()[0];

        Student student = service.read(studentId);

        String message = String.format(UPDATE_MESSAGE, student.getFullName());

        EmailMessage emailMessage = new EmailMessage(student.getEmail(), message);

        webClient.post()
                .uri("update")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(BodyInserters.fromValue(emailMessage))
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }

    @AfterReturning("callAfterAddGrade()")
    public void sendAfterAddGrade(JoinPoint point) {
        Object[] args = point.getArgs();
        Integer studentId = (Integer) args[0];
        Integer gradeId = (Integer) args[1];

        Student student = service.read(studentId);
        Grade grade = gradeClient.readGrade(gradeId);

        String message = String.format(GRADE_MESSAGE, student.getFullName(), grade.getName(),
                grade.getStartDate().toString());

        EmailMessage emailMessage = new EmailMessage(student.getEmail(), message);

        webClient.post()
                .uri("grade")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(BodyInserters.fromValue(emailMessage))
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }

    @AfterReturning("callAfterCreateComment()")
    public void sendAfterCreateComment(JoinPoint point) {
        Object[] args = point.getArgs();
        Integer studentId = (Integer) args[0];
        Integer gradeId = (Integer) args[1];

        Student student = service.read(studentId);
        Grade grade = gradeClient.readGrade(gradeId);

        String message = String.format(COMMENT_MESSAGE, grade.getName());

        EmailMessage emailMessage = new EmailMessage(student.getEmail(), message);

        webClient.post()
                .uri("comment")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(BodyInserters.fromValue(emailMessage))
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }

    private static final String REGISTRATION_MESSAGE = "Спасибо, %s, за регистрацию на нашей платформе. Удачи вам в обучении!";

    private static final String UPDATE_MESSAGE = "%s, ваши персональные данные были изменены. Если это были не вы, сообщите поддержке!";

    private static final String GRADE_MESSAGE = "Благодарим вас, %s, за запись на курс \"%s\". Дата начала обучения: %s.";

    private static final String COMMENT_MESSAGE = "Вы успешно оставили комментарий к курсу \"%s\". Благодарим за обратную связь!";
}