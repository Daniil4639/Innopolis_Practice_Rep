package app.services_tests.abstracts;

import app.clients.GradeClient;
import app.repositories.StudentRepository;
import app.services.StudentService;
import org.mockito.Mockito;

public abstract class StudentServiceAbstractTest {

    protected final GradeClient gradeClient;
    protected final StudentRepository studentRepository;
    protected final StudentService service;

    public StudentServiceAbstractTest() {
        studentRepository = Mockito.mock(StudentRepository.class);
        gradeClient = Mockito.mock(GradeClient.class);
        service = new StudentService(studentRepository, gradeClient);
    }
}
