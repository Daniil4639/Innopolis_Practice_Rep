package bus_app.test_repositories.department_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Department;
import bus_app.repositories.DepartmentRepository;
import bus_app.utils.LoggerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DepartmentIncorrectTest extends TestDBContainerInitializer {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования ошибок в 'Departments'!");
    }

    @Test
    @DisplayName("Departments: 'create item' error test")
    public void testErrorCreateItem() {
        Department department = new Department(null, null, "some address");

        try {
            repository.createItem(department);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Departments: 'find by id' error test")
    public void testErrorFindById() {
        try {
            repository.findItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Departments: 'update by id' error test")
    public void testErrorUpdateById() {
        Department department = new Department(null, null, "some address");

        try {
            repository.updateItemById(99999999, department);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Departments: 'delete by id' error test")
    public void testErrorDeleteById() {
        try {
            repository.deleteItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }
}
