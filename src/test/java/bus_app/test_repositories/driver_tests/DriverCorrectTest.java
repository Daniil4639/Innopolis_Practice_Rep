package bus_app.test_repositories.driver_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.mappers.DriverMapper;
import bus_app.model.Driver;
import bus_app.repositories.DriverRepository;
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
public class DriverCorrectTest extends TestDBContainerInitializer {

    @Autowired
    private DriverRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования 'Drivers'!");
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers: 'create' test")
    public void testCreateItem() {
        Driver driver = new Driver(null, "someName", 42, "somePhone");
        Integer id = repository.createItem(driver).getId();

        Driver receivedDriver = template.queryForObject(
                "select * from drivers where id = ?", new DriverMapper(), id
        );

        assertThat(driver.getAge()).isEqualTo(receivedDriver.getAge());
        assertThat(driver.getName()).isEqualTo(receivedDriver.getName());
        assertThat(driver.getPhone()).isEqualTo(receivedDriver.getPhone());

        template.execute(String.format("delete from drivers where id = %d", id));
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers: 'find by id' test")
    public void testFindItemById() {
        Driver someDriver = template.queryForObject(
                "select * from drivers limit 1 offset 1", new DriverMapper()
        );

        Driver driver = repository.findItemById(someDriver.getId());

        assertThat(driver.getName()).isEqualTo(someDriver.getName());
        assertThat(driver.getAge()).isEqualTo(someDriver.getAge());
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers: 'find all items' test")
    public void testFindAllItems() {
        List<Driver> drivers = repository.findAllItems();

        assertThat(drivers.size()).isEqualTo(4);
        assertThat(drivers.stream().map(Driver::getName).toList().containsAll(
                List.of("Никитников Ринат", "Хусид Глеб", "Зарубин Тимофей", "Гилев Юрий")
        )).isTrue();
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers: 'update item' test")
    public void testUpdateItem() {
        Driver oldInfo = template.queryForObject("select * from drivers limit 1", new DriverMapper());
        Integer oldAge = oldInfo.getAge();
        oldInfo.setAge(50);

        Driver newInfo = repository.updateItemById(oldInfo.getId(), oldInfo);

        assertThat(newInfo.getAge()).isEqualTo(50);

        oldInfo.setAge(oldAge);
        repository.updateItemById(oldInfo.getId(), oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers: 'delete by id' test")
    public void testDeleteById() {
        Driver oldInfo = template.queryForObject("select * from drivers limit 1 offset 3", new DriverMapper());

        repository.deleteItemById(oldInfo.getId());

        Integer checkCount = template.queryForObject(
                "select count(*) from drivers where id = ?", Integer.class, oldInfo.getId()
        );

        assertThat(checkCount).isEqualTo(0);

        repository.createItem(oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Drivers: 'delete all items' test")
    public void testDeleteAllItems() {
        List<Driver> oldInfo = repository.findAllItems();

        repository.deleteAllItems();

        Integer checkCount = template.queryForObject(
                "select count(*) from drivers", Integer.class
        );

        assertThat(checkCount).isEqualTo(0);

        for (Driver driver: oldInfo) {
            repository.createItem(driver);
        }
    }
}