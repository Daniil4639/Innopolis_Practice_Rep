package app.services;

import app.aspects.LogExecTime;
import app.clients.GradeClient;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.repositories.StudentRepository;
import app.services.interfaces.BasedCRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements BasedCRUDService<Student> {

    private final StudentRepository studentRepository;
    private final GradeClient gradeClient;

    public StudentService(StudentRepository studentRepository, GradeClient gradeClient) {
        this.studentRepository = studentRepository;
        this.gradeClient = gradeClient;
    }

    @Override
    @LogExecTime
    public Student create(Student obj) throws IncorrectBodyException, NoDataException {
        Student.isStudentCorrect(obj);
        for (Integer gradeId: obj.gradesList()) {
            checkGradeId(gradeId);
        }

        return studentRepository.createStudent(obj);
    }

    @Override
    public Student read(Integer id) throws NoDataException {
        return studentRepository.readStudent(id);
    }

    @Override
    public Student update(Integer id, Student obj) throws IncorrectBodyException, NoDataException {
        if (obj.gradesList() != null) {
            for (Integer gradeId: obj.gradesList()) {
                checkGradeId(gradeId);
            }
        }

        Student.checkValidation(obj);

        try {
            studentRepository.readStudent(id);
        } catch (NoDataException ex) {
            return studentRepository.createStudent(obj);
        }

        return studentRepository.updateStudent(id, obj);
    }

    @Override
    public boolean delete(Integer id) throws NoDataException {
        studentRepository.readStudent(id);

        return studentRepository.deleteStudent(id);
    }

    @LogExecTime
    public Student addGrade(Integer id, Integer gradeId) throws NoDataException {
        checkGradeId(gradeId);

        return studentRepository.addGrade(id, gradeId);
    }

    @LogExecTime
    public List<Student> getAllStudents(Integer id) throws NoDataException {
        checkGradeId(id);
        return studentRepository.getAllStudentsByGrade(id);
    }

    private void checkGradeId(Integer id) throws NoDataException {
        gradeClient.readGrade(id);
    }
}
