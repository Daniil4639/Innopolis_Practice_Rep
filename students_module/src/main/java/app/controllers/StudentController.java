package app.controllers;

import app.configs.web_config.SessionData;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoAuthorizationException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.services.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;
    private final SessionData sessionData;

    public StudentController(StudentService service, SessionData sessionData) {
        this.service = service;
        this.sessionData = sessionData;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) throws IncorrectBodyException, NoDataException {
        Student newStudent = service.create(student);
        sessionData.setSessionId(newStudent.id());

        return newStudent;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Integer id) throws NoDataException {
        Student newStudent = service.read(id);
        sessionData.setSessionId(newStudent.id());

        return newStudent;
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Integer id, @RequestBody Student student)
            throws IncorrectBodyException, NoDataException, NoAuthorizationException {
        if (sessionData.getSessionId() == null || !sessionData.getSessionId().equals(id)) {
            throw new NoAuthorizationException("No authorization!");
        }

        return service.update(id, student);
    }

    @PutMapping("/{id}/{grade_id}")
    public Student addGrade(@PathVariable("id") Integer id, @PathVariable("grade_id") Integer gradeId)
            throws NoDataException, NoAuthorizationException {
        if (sessionData.getSessionId() == null || !sessionData.getSessionId().equals(id)) {
            throw new NoAuthorizationException("No authorization!");
        }

        return service.addGrade(id, gradeId);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, HttpSession session) throws NoDataException {
        service.delete(id);

        session.invalidate();

        return "Record has been deleted!";
    }

    @DeleteMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping
    public List<Student> getAllStudents(@RequestParam("grade_id") Integer id) throws NoDataException {
        return service.getAllStudents(id);
    }
}
