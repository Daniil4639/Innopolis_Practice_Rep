package bus_app.test_repositories.bus_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.mappers.BusMapper;
import bus_app.model.Bus;
import bus_app.repositories.BusRepository;
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
public class BusCorrectTest extends TestDBContainerInitializer {

    @Autowired
    private BusRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования 'Buses'!");
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses: 'create' test")
    public void testCreateItem() {
        Bus bus = new Bus(null, "k12", null, null, 2, 31, "бензин");
        Integer id = repository.createItem(bus).getId();

        Bus receivedBus = template.queryForObject(
                "select * from buses where id = ?", new BusMapper(), id
        );

        assertThat(bus.getType()).isEqualTo(receivedBus.getType());
        assertThat(bus.getNumber()).isEqualTo(receivedBus.getNumber());
        assertThat(bus.getSeatsNumber()).isEqualTo(receivedBus.getSeatsNumber());

        template.execute(String.format("delete from buses where id = %d", receivedBus.getId()));
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses: 'find by id' test")
    public void testFindItemById() {
        Bus someBus = template.queryForObject(
                "select * from buses limit 1 offset 1", new BusMapper()
        );

        Bus bus = repository.findItemById(someBus.getId());

        assertThat(bus.getNumber()).isEqualTo(someBus.getNumber());
        assertThat(bus.getSeatsNumber()).isEqualTo(someBus.getSeatsNumber());
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses: 'find all items' test")
    public void testFindAllItems() {
        List<Bus> buses = repository.findAllItems();

        assertThat(buses.size()).isEqualTo(4);
        assertThat(buses.stream().map(Bus::getNumber).toList().containsAll(
                List.of("м3", "265", "с799", "403")
        )).isTrue();
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses: 'update item' test")
    public void testUpdateItem() {
        Bus oldInfo = template.queryForObject("select * from buses limit 1", new BusMapper());
        Integer oldNumberOfSeats = oldInfo.getSeatsNumber();
        oldInfo.setSeatsNumber(20);

        Bus newInfo = repository.updateItemById(oldInfo.getId(), oldInfo);

        assertThat(newInfo.getSeatsNumber()).isEqualTo(20);

        oldInfo.setSeatsNumber(oldNumberOfSeats);
        repository.updateItemById(oldInfo.getId(), oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses: 'delete by id' test")
    public void testDeleteById() {
        Bus oldInfo = template.queryForObject("select * from buses limit 1 offset 3", new BusMapper());

        repository.deleteItemById(oldInfo.getId());

        Integer checkCount = template.queryForObject(
                "select count(*) from buses where id = ?", Integer.class, oldInfo.getId()
        );

        assertThat(checkCount).isEqualTo(0);

        repository.createItem(oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Buses: 'delete all items' test")
    public void testDeleteAllItems() {
        List<Bus> oldInfo = repository.findAllItems();

        repository.deleteAllItems();

        Integer checkCount = template.queryForObject(
                "select count(*) from buses", Integer.class
        );

        assertThat(checkCount).isEqualTo(0);

        for (Bus bus: oldInfo) {
            repository.createItem(bus);
        }
    }
}