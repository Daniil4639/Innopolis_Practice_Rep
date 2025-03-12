package bus_app.test_repositories.station_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Station;
import bus_app.repositories.StationRepository;
import bus_app.utils.LoggerController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class StationIncorrectTest extends TestDBContainerInitializer {

    @Autowired
    private StationRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования ошибок в 'Stations'!");
    }

    @Test
    @DisplayName("Stations: 'create item' error test")
    public void testErrorCreateItem() {
        Station station = new Station(null, null, "someDistrict");

        try {
            repository.createItem(station);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Stations: 'find by id' error test")
    public void testErrorFindById() {
        try {
            repository.findItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }

    @Test
    @DisplayName("Stations: 'update by id' error test")
    public void testErrorUpdateById() {
        Station station = new Station(null, null, "someDistrict");

        try {
            repository.updateItemById(99999999, station);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @DisplayName("Stations: 'delete by id' error test")
    public void testErrorDeleteById() {
        try {
            repository.deleteItemById(9999999);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }
}
