package bus_app.test_repositories.driver_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Driver;
import bus_app.repositories.DriverRepository;
import bus_app.utils.LoggerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DriverIncorrectTest extends TestDBContainerInitializer {

    @Autowired
    private DriverRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования ошибок в 'Drivers'!");
    }

    @Test
    @DisplayName("Drivers: 'create item' error test")
    public void testErrorCreateItem() {
        Driver driver = new Driver(null, "someName", null, "somePhone");

        try {
            repository.createItem(driver);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Drivers: 'find by id' error test")
    public void testErrorFindById() {
        try {
            repository.findItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Drivers: 'update by id' error test")
    public void testErrorUpdateById() {
        Driver driver = new Driver(null, "someName", null, "somePhone");

        try {
            repository.updateItemById(99999999, driver);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Drivers: 'delete by id' error test")
    public void testErrorDeleteById() {
        try {
            repository.deleteItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }
}
