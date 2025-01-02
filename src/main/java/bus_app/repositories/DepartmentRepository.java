package bus_app.repositories;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.mappers.DepartmentMapper;
import bus_app.model.Department;
import bus_app.packages.DepartmentQueriesPackage;
import bus_app.repositories.interfaces.DefaultDBRepository;
import bus_app.utils.LoggerController;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentRepository implements DefaultDBRepository<Department> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Department createItem(Department item) throws IncorrectBodyException {
        Department.isDepartmentDataCorrect(item);

        return jdbcTemplate.queryForObject(
                String.format(DepartmentQueriesPackage.ADD_DEPARTMENT,
                        ("'" + item.getName() + "'"),
                        ("'" + item.getAddress() + "'")),
                new DepartmentMapper()
        );
    }

    @Override
    public Department findItemById(Integer id) throws NoDataException {
        try {
            return jdbcTemplate.queryForObject(
                    String.format(DepartmentQueriesPackage.GET_DEPARTMENT_BY_ID, id),
                    new DepartmentMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("Запись в таблице 'Departments' с идентификатором '" +
                    id + "' отсутствует!");
        }
    }

    @Override
    public List<Department> findAllItems() {
        return jdbcTemplate.queryForStream(
                DepartmentQueriesPackage.GET_ALL_DEPARTMENTS,
                new DepartmentMapper()
        ).toList();
    }

    @Override
    public Department updateItemById(Integer id, Department item) throws IncorrectBodyException {
        try {
            findItemById(id);
        } catch (NoDataException ex) {
            LoggerController.makeRecord("В таблице 'Departments' запись с индексом '" + id + "' отсутствует!");
            return createItem(item);
        }

        return jdbcTemplate.queryForObject(
                String.format(DepartmentQueriesPackage.UPDATE_DEPARTMENT_BY_ID,
                        ((item.getName() != null) ? ("'" + item.getName() + "'") : ("name")),
                        ((item.getAddress() != null) ? ("'" + item.getAddress() + "'") : ("address")),
                        id),
                new DepartmentMapper()
        );
    }

    @Override
    public void deleteItemById(Integer id) throws NoDataException {
        findItemById(id);

        jdbcTemplate.execute(String.format(
                "update buses set department_id = null where department_id = %d",
                id
        ));

        jdbcTemplate.execute(String.format(DepartmentQueriesPackage.DELETE_DEPARTMENT_BY_ID, id));
    }

    @Override
    public void deleteAllItems() {
        jdbcTemplate.execute("update buses set department_id = null");
        jdbcTemplate.execute(DepartmentQueriesPackage.DELETE_ALL_DEPARTMENTS);
    }
}
