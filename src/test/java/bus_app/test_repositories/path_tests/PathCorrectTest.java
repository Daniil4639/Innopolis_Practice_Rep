package bus_app.test_repositories.path_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.mappers.PathMapper;
import bus_app.model.Path;
import bus_app.repositories.PathRepository;
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
public class PathCorrectTest extends TestDBContainerInitializer {

    @Autowired
    private PathRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();

        LoggerController.makeRecord("Выполена миграция для тестирования 'Paths'!");
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths: 'create' test")
    public void testCreateItem() {
        Path path = new Path(null, 6, 2, 140000);
        Integer id = repository.createItem(path).getId();

        Path receivedPath = template.queryForObject(
                "select * from paths where id = ?", new PathMapper(), id
        );

        assertThat(path.getBeginStation()).isEqualTo(receivedPath.getBeginStation());
        assertThat(path.getEndStation()).isEqualTo(receivedPath.getEndStation());
        assertThat(path.getDuration()).isEqualTo(receivedPath.getDuration());

        template.execute(String.format("delete from paths where id = %d", id));
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths: 'find by id' test")
    public void testFindItemById() {
        Path somePath = template.queryForObject(
                "select * from paths limit 1 offset 1", new PathMapper()
        );

        Path path = repository.findItemById(somePath.getId());

        assertThat(path.getDuration()).isEqualTo(somePath.getDuration());
        assertThat(somePath.getBeginStation()).isEqualTo(somePath.getBeginStation());
        assertThat(somePath.getEndStation()).isEqualTo(somePath.getEndStation());
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths: 'find all items' test")
    public void testFindAllItems() {
        List<Path> paths = repository.findAllItems();

        assertThat(paths.size()).isEqualTo(4);
        assertThat(paths.stream().map(Path::getDuration).toList().containsAll(
                List.of(5400000, 2400000, 3600000, 4860000)
        )).isTrue();
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths: 'update item' test")
    public void testUpdateItem() {
        Path oldInfo = template.queryForObject("select * from paths limit 1", new PathMapper());
        Integer oldDuration = oldInfo.getDuration();
        oldInfo.setDuration(1);

        Path newInfo = repository.updateItemById(oldInfo.getId(), oldInfo);

        assertThat(newInfo.getDuration()).isEqualTo(1);

        oldInfo.setDuration(oldDuration);
        repository.updateItemById(oldInfo.getId(), oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths: 'delete by id' test")
    public void testDeleteById() {
        Path oldInfo = template.queryForObject("select * from paths limit 1 offset 3", new PathMapper());

        repository.deleteItemById(oldInfo.getId());

        Integer checkCount = template.queryForObject(
                "select count(*) from paths where id = ?", Integer.class, oldInfo.getId()
        );

        assertThat(checkCount).isEqualTo(0);

        repository.createItem(oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Paths: 'delete all items' test")
    public void testDeleteAllItems() {
        List<Path> oldInfo = repository.findAllItems();

        repository.deleteAllItems();

        Integer checkCount = template.queryForObject(
                "select count(*) from paths", Integer.class
        );

        assertThat(checkCount).isEqualTo(0);

        for (Path path: oldInfo) {
            repository.createItem(path);
        }
    }
}