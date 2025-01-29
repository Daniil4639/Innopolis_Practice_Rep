package app.controllers;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) throws IncorrectBodyException, NoDataException {
        return repository.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Integer id) throws NoDataException {
        return repository.readStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable("id") Integer id, @RequestBody Student student) throws IncorrectBodyException, NoDataException {
        return repository.updateStudent(id, student);
    }

    @PutMapping("/{id}/{grade_id}")
    public Student addGrade(@PathVariable("id") Integer id, @PathVariable("grade_id") Integer gradeId) throws NoDataException {
        return repository.addGrade(id, gradeId);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") Integer id) throws NoDataException {
        repository.deleteStudent(id);

        return "Record has been deleted!";
    }
}
