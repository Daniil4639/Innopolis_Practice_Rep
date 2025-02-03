package app.services_tests;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.services_tests.abstracts.StudentServiceAbstractTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class StudentServiceIncorrectTest extends StudentServiceAbstractTest {

    public StudentServiceIncorrectTest() {
        super();
    }

    @Test
    @DisplayName("Students: service gets null | create")
    public void createNullTest() throws NoDataException {
        Student student = new Student(
                1, null, "test_email@test.ru", new Integer[] {1}
        );

        try {
            service.create(student);
            assert false;
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Students: service invalidation | create")
    public void createInvalidationTest() throws NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName", "test_email@test.ru", new Integer[] {1}
        );

        try {
            service.create(student);
            assert false;
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Students: service gets incorrect grades | create")
    public void createIncorrectGradesTest() throws IncorrectBodyException, NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName TestThirdName", "test_email@test.ru", new Integer[] {1}
        );

        Mockito.when(gradeRepository.gradeIsExist(1)).thenThrow(NoDataException.class);

        try {
            service.create(student);
            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: service throw ex | read")
    public void readExceptionTest() throws NoDataException {
        Mockito.when(studentRepository.readStudent(1)).thenThrow(NoDataException.class);

        try {
            service.read(1);
            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: service invalidation | update")
    public void updateInvalidationTest() throws NoDataException {
        Student student = new Student(
                1, "TestName TestSecondName", "test_email@test.ru", new Integer[] {1}
        );

        try {
            service.update(1, student);
            assert false;
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Students: service gets incorrect grades | update")
    public void updateIncorrectGradesTest() throws IncorrectBodyException, NoDataException {
        Student student = new Student(
                1, null, null, new Integer[] {1}
        );

        Mockito.when(gradeRepository.gradeIsExist(1)).thenThrow(NoDataException.class);

        try {
            service.update(1, student);
            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: service no data | delete")
    public void deleteNoDataTest() throws NoDataException {
        Mockito.when(studentRepository.readStudent(1)).thenThrow(NoDataException.class);

        try {
            service.delete(1);
            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: service doesn't see a grade | addGrade")
    public void addGradeIncorrectTest() throws NoDataException {
        Mockito.when(gradeRepository.gradeIsExist(3)).thenThrow(NoDataException.class);

        try {
            service.addGrade(1, 3);
            assert false;
        } catch (NoDataException ignored) {}
    }
}
