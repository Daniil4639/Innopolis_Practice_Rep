package app.services_tests.abstracts;

import app.repositories.GradeRepository;
import app.repositories.StudentRepository;
import app.services.StudentService;
import org.mockito.Mockito;

public abstract class StudentServiceAbstractTest {

    protected final GradeRepository gradeRepository;
    protected final StudentRepository studentRepository;
    protected final StudentService service;

    public StudentServiceAbstractTest() {
        studentRepository = Mockito.mock(StudentRepository.class);
        gradeRepository = Mockito.mock(GradeRepository.class);
        service = new StudentService(studentRepository, gradeRepository);
    }
}
