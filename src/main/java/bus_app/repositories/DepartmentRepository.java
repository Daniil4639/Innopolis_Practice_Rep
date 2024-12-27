package bus_app.repositories;

import bus_app.mappers.DepartmentMapper;
import bus_app.model.Department;
import bus_app.packages.QueriesPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public Department insertNewDepartment(String name, String address) {
        Integer maxId = jdbcTemplate.queryForObject(
                "select max(id) from bus_db.departments",
                Integer.class
        );

        jdbcTemplate.execute(
                String.format(QueriesPackage.INSERT_NEW_DEPARTMENT,
                        maxId + 1, name, address)
        );

        return jdbcTemplate.queryForObject(
                String.format("select * from bus_db.departments where id = %d", maxId + 1),
                new DepartmentMapper()
        );
    }

    public void deleteDepartmentById(Integer id) {
        jdbcTemplate.execute(
                String.format(QueriesPackage.DELETE_DEPARTMENT_BY_ID, id, id)
        );
    }
}
