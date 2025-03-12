package bus_app.test_repositories.department_tests;

import bus_app.TestDBContainerInitializer;
import bus_app.mappers.DepartmentMapper;
import bus_app.model.Department;
import bus_app.repositories.DepartmentRepository;
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
public class DepartmentCorrectTest extends TestDBContainerInitializer {

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
    @SneakyThrows
    @DisplayName("Departments: 'create item' test")
    public void testCreateItem() {
        Department department = new Department(null, "newDep", "newAddress");

        Department info = repository.createItem(department);

        assertThat(info.getName()).isEqualTo(department.getName());
        assertThat(info.getAddress()).isEqualTo(department.getAddress());

        template.execute(String.format("delete from departments where id = %d", info.getId()));
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments: 'find by id' test")
    public void testFindById() {
        Department department = template.queryForObject(
                "select * from departments limit 1 offset 1", new DepartmentMapper()
        );

        Department checkDep = repository.findItemById(department.getId());
        assertThat(checkDep.getName()).isEqualTo(department.getName());
        assertThat(checkDep.getAddress()).isEqualTo(department.getAddress());
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments: 'find all items' test")
    public void testFindAllItems() {
        List<Department> departments = repository.findAllItems();

        assertThat(departments.size()).isEqualTo(2);
        assertThat(departments.stream().map(Department::getName).toList().containsAll(
                List.of("Департамент №1", "Департамент №2")
        )).isTrue();
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments: 'update item by id' test")
    public void testUpdateItem() {
        Department oldInfo = template
                .queryForObject("select * from departments limit 1", new DepartmentMapper());

        String oldAddress = oldInfo.getAddress();
        oldInfo.setAddress("some address");

        Department newInfo = repository.updateItemById(oldInfo.getId(), oldInfo);

        assertThat(newInfo.getAddress()).isEqualTo(oldInfo.getAddress());

        oldInfo.setAddress(oldAddress);
        repository.updateItemById(oldInfo.getId(), oldInfo);
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments: 'delete by id' test")
    public void testDeleteById() {
        Department department = template.queryForObject(
                "select * from departments limit 1 offset 1", new DepartmentMapper()
        );

        repository.deleteItemById(department.getId());

        Integer checkCount = template.queryForObject(
                "select count(*) from departments where id = ?", Integer.class, department.getId()
        );

        assertThat(checkCount).isEqualTo(0);

        repository.createItem(department);
    }

    @Test
    @SneakyThrows
    @DisplayName("Departments: 'delete all items' test")
    public void testDeleteAllItems() {
        List<Department> departments = repository.findAllItems();

        repository.deleteAllItems();

        Integer checkCount = template.queryForObject(
                "select count(*) from departments", Integer.class
        );

        assertThat(checkCount).isEqualTo(0);

        for (Department department: departments) {
            repository.createItem(department);
        }
    }
}
