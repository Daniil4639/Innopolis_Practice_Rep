package app.services_tests;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.services_tests.abstracts.StudentServiceAbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class StudentServiceCorrectTest extends StudentServiceAbstractTest {

    public StudentServiceCorrectTest() {
        super();
    }

    @Test
    @DisplayName("Students: service correct create")
    public void createTest() throws IncorrectBodyException, NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", "test_email@test.ru", new Integer[] {1}
        );

        Mockito.when(studentRepository.createStudent(student)).thenReturn(student);

        Student receivedStudent = service.create(student);

        assert receivedStudent.fullName().equals(student.fullName());
        assert receivedStudent.email().equals(student.email());
    }

    @Test
    @DisplayName("Students: service correct read")
    public void readTest() throws NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", "test_email@test.ru", new Integer[] {1}
        );

        Mockito.when(studentRepository.readStudent(1)).thenReturn(student);

        Student receivedStudent = service.read(1);

        assert receivedStudent.fullName().equals(student.fullName());
        assert receivedStudent.email().equals(student.email());
    }

    @Test
    @DisplayName("Students: service correct update")
    public void updateTest() throws IncorrectBodyException, NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", "test_email@test.ru", new Integer[] {1}
        );

        Mockito.when(studentRepository.updateStudent(1, student)).thenReturn(student);

        Student receivedStudent = service.update(1, student);

        assert receivedStudent.fullName().equals(student.fullName());
        assert receivedStudent.email().equals(student.email());
    }

    @Test
    @DisplayName("Students: service correct delete")
    public void deleteTest() throws NoDataException {
        Mockito.when(studentRepository.deleteStudent(1)).thenReturn(true);

        assert service.delete(1);
    }

    @Test
    @DisplayName("Students: service correct add grade")
    public void addGradeTest() throws NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", "test_email@test.ru", new Integer[] {1, 2}
        );

        Mockito.when(studentRepository.addGrade(1, 2)).thenReturn(student);

        Student receivedStudent = service.addGrade(1, 2);

        assert Arrays.equals(receivedStudent.gradesList(), student.gradesList());
    }
}
