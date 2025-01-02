package bus_app.test_repositories.bus_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Bus;
import bus_app.repositories.BusRepository;
import bus_app.utils.LoggerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BusIncorrectTest extends TestDBContainerInitializer {

    @Autowired
    private BusRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования ошибок в 'Buses'!");
    }

    @Test
    @DisplayName("Buses: 'create item' error test")
    public void testErrorCreateItem() {
        Bus bus = new Bus(null, "k23", null, null, null, null, "бензин");

        try {
            repository.createItem(bus);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Buses: 'find by id' error test")
    public void testErrorFindById() {
        try {
            repository.findItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Buses: 'update by id' error test")
    public void testErrorUpdateById() {
        Bus bus = new Bus(null, "k23", null, null, null, null, "бензин");

        try {
            repository.updateItemById(99999999, bus);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Buses: 'delete by id' error test")
    public void testErrorDeleteById() {
        try {
            repository.deleteItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }
}
