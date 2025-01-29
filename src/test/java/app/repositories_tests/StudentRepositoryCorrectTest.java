package app.repositories_tests;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.mappers.StudentMapper;
import app.models.Student;
import app.repositories.GradeRepository;
import app.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StudentRepositoryCorrectTest extends TestDBContainerInitializer {

    private final StudentRepository repository;
    private final JdbcTemplate template;

    public StudentRepositoryCorrectTest() {
        template = new JdbcTemplate(TestDBContainerInitializer.source);
        repository = new StudentRepository(template, new GradeRepository(template));
    }

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Students: correct create test")
    public void testCreate() throws IncorrectBodyException, NoDataException {
        Student student = repository.createStudent(new Student(
                null, "testName testSecondName testThirdName", "test_email@test.ru", new Integer[] {1, 2, 3}
        ));

        Student receivedStudent = template.queryForObject(
                "select * from students where id = ?",
                new StudentMapper(),
                student.id()
        );

        assert (student.email().equals(receivedStudent.email()));
        assert (student.fullName().equals(receivedStudent.fullName()));
        assert (Arrays.equals(student.gradesList(), receivedStudent.gradesList()));
    }

    @Test
    @DisplayName("Students: correct read test")
    public void testRead() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 'test_email', ARRAY[1]) returning id",
                Integer.class
        );

        Student student = repository.readStudent(id);
        assert (student.fullName().equals("test_user"));
        assert (student.email().equals("test_email"));
        assert (Arrays.stream(student.gradesList()).collect(Collectors.toSet()).contains(1));
    }

    @Test
    @DisplayName("Students: correct update test")
    public void updateTest() throws IncorrectBodyException, NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 'test_email', ARRAY[1]) returning id",
                Integer.class
        );

        Student student = repository.updateStudent(id,
                new Student(null, "newTestName testSecondName testThirdName", null, null));

        assert (student.fullName().equals("newTestName testSecondName testThirdName"));
    }

    @Test
    @DisplayName("Students: correct add grade test")
    public void addGradeTest() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 'test_email', ARRAY[1]) returning id",
                Integer.class
        );

        Student student = repository.addGrade(id, 2);

        assert (Arrays.stream(student.gradesList()).collect(Collectors.toSet()).contains(2));
    }

    @Test
    @DisplayName("Students: correct delete test")
    public void deleteTest() throws NoDataException {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 'test_email', ARRAY[1]) returning id",
                Integer.class
        );

        repository.deleteStudent(id);

        try {
            template.queryForObject(
                    "select * from students where id = ?",
                    new StudentMapper(),
                    id
            );

            assert false;
        } catch (EmptyResultDataAccessException ignored) {}
    }
}
