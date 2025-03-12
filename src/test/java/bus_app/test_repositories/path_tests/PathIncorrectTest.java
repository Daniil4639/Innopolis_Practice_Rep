package bus_app.test_repositories.path_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Path;
import bus_app.repositories.PathRepository;
import bus_app.utils.LoggerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PathIncorrectTest extends TestDBContainerInitializer {

    @Autowired
    private PathRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования ошибок в 'Paths'!");
    }

    @Test
    @DisplayName("Paths: 'create item' error test")
    public void testErrorCreateItem() {
        Path path = new Path(null, 6, 2, null);

        try {
            repository.createItem(path);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Paths: 'find by id' error test")
    public void testErrorFindById() {
        try {
            repository.findItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Paths: 'update by id' error test")
    public void testErrorUpdateById() {
        Path path = new Path(null, 6, 2, null);

        try {
            repository.updateItemById(99999999, path);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Paths: 'delete by id' error test")
    public void testErrorDeleteById() {
        try {
            repository.deleteItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }
}
