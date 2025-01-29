package app.repositories_tests;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Student;
import app.repositories.GradeRepository;
import app.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class StudentRepositoryIncorrectTest extends TestDBContainerInitializer {

    private final StudentRepository repository;
    private final JdbcTemplate template;

    public StudentRepositoryIncorrectTest() {
        template = new JdbcTemplate(TestDBContainerInitializer.source);
        repository = new StudentRepository(template, new GradeRepository(template));
    }

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Students: incorrect create test")
    public void createTest() {
        try {
            repository.createStudent(new Student(
                    null, null, "test_email", new Integer[] {1}));

            assert false;
        } catch (IncorrectBodyException | NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: incorrect read test")
    public void readTest() {
        try {
            repository.readStudent(9999);

            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: incorrect update test")
    public void updateTest() {
        try {
            repository.updateStudent(9999, new Student(
                    null, null, "test_email", new Integer[] {1}));

            assert false;
        } catch (IncorrectBodyException | NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: incorrect add grade test")
    public void addGradeTest() {
        Integer id = template.queryForObject(
                "insert into students values(default, 'test_user', 'test_email', ARRAY[1]) returning id",
                Integer.class
        );

        try {
            repository.addGrade(id, 9999);

            assert false;
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Students: incorrect delete test")
    public void deleteTest() {
        try {
            repository.deleteStudent(9999);

            assert false;
        } catch (NoDataException ignored) {}
    }
}