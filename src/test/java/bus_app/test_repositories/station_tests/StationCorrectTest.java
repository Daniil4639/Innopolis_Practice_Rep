package bus_app.test_repositories.station_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.mappers.StationMapper;
import bus_app.model.Station;
import bus_app.repositories.StationRepository;
import bus_app.utils.LoggerController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class StationCorrectTest extends TestDBContainerInitializer {

    @Autowired
    private StationRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования 'Stations'!");
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations: 'create' test")
    public void testCreateItem() {
        Station station = new Station(null, "someName", "someDistrict");
        Integer id = repository.createItem(station).getId();

        Station receivedStation = template.queryForObject(
                "select * from stations where id = ?", new StationMapper(), id
        );

        assertThat(station.getDistrict()).isEqualTo(receivedStation.getDistrict());
        assertThat(station.getName()).isEqualTo(receivedStation.getName());

        template.execute(String.format("delete from stations where id = %d", id));
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations: 'find by id' test")
    public void testFindItemById() {
        Station someStation = template.queryForObject(
                "select * from stations limit 1 offset 1", new StationMapper()
        );

        Station station = repository.findItemById(someStation.getId());

        assertThat(station.getName()).isEqualTo(someStation.getName());
        assertThat(station.getDistrict()).isEqualTo(someStation.getDistrict());
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations: 'find all items' test")
    public void testFindAllItems() {
        List<Station> stations = repository.findAllItems();

        assertThat(stations.size()).isEqualTo(16);
        assertThat(stations.stream().map(Station::getName).toList().containsAll(
                List.of("Серебряный бор", "Мосгорсуд", "Гидропроект", "Префектура ЮАО")
        )).isTrue();
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations: 'update item' test")
    public void testUpdateItem() {
        Station oldInfo = template.queryForObject("select * from stations limit 1", new StationMapper());
        String oldName = oldInfo.getName();
        oldInfo.setName("someName");

        Station newInfo = repository.updateItemById(oldInfo.getId(), oldInfo);

        assertThat(newInfo.getName()).isEqualTo("someName");

        oldInfo.setName(oldName);
        repository.updateItemById(oldInfo.getId(), oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations: 'delete by id' test")
    public void testDeleteById() {
        Station oldInfo = template.queryForObject("select * from stations limit 1 offset 3", new StationMapper());

        repository.deleteItemById(oldInfo.getId());

        Integer checkCount = template.queryForObject(
                "select count(*) from stations where id = ?", Integer.class, oldInfo.getId()
        );

        assertThat(checkCount).isEqualTo(0);

        repository.createItem(oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Stations: 'delete all items' test")
    public void testDeleteAllItems() {
        List<Station> oldInfo = repository.findAllItems();

        repository.deleteAllItems();

        Integer checkCount = template.queryForObject(
                "select count(*) from stations", Integer.class
        );

        assertThat(checkCount).isEqualTo(0);

        for (Station station: oldInfo) {
            repository.createItem(station);
        }
    }
}