package app.services;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.repositories.GradeRepository;
import app.repositories.StudentRepository;
import app.services.interfaces.BasedCRUDService;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements BasedCRUDService<Student> {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Student create(Student obj) throws IncorrectBodyException, NoDataException {
        Student.isStudentCorrect(obj);
        checkGradeIds(obj.gradesList());

        return studentRepository.createStudent(obj);
    }

    @Override
    public Student read(Integer id) throws NoDataException {
        return studentRepository.readStudent(id);
    }

    @Override
    public Student update(Integer id, Student obj) throws IncorrectBodyException, NoDataException {
        if (obj.gradesList() != null) {
            checkGradeIds(obj.gradesList());
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

    public Student addGrade(Integer id, Integer gradeId) throws NoDataException {
        gradeRepository.gradeIsExist(gradeId);

        return studentRepository.addGrade(id, gradeId);
    }

    private void checkGradeIds(Integer[] gradesList) throws NoDataException {
        for (Integer id: gradesList) {
            gradeRepository.gradeIsExist(id);
        }
    }
}
