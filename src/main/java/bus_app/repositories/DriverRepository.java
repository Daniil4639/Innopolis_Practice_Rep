package bus_app.repositories;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.mappers.DriverMapper;
import bus_app.model.Driver;
import bus_app.packages.DriverQueriesPackage;
import bus_app.repositories.interfaces.DefaultDBRepository;
import bus_app.utils.LoggerController;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DriverRepository implements DefaultDBRepository<Driver> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Driver createItem(Driver item) throws IncorrectBodyException {
        Driver.isDriverDataCorrect(item);

        return jdbcTemplate.queryForObject(
                String.format(DriverQueriesPackage.ADD_DRIVER,
                        ("'" + item.getName() + "'"),
                        (item.getAge()),
                        ("'" + item.getPhone() + "'")),
                new DriverMapper()
        );
    }

    @Override
    public Driver findItemById(Integer id) throws NoDataException {
        try {
            return jdbcTemplate.queryForObject(
                    String.format(DriverQueriesPackage.GET_DRIVER_BY_ID, id),
                    new DriverMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("Запись в таблице 'Drivers' с идентификатором '" +
                    id + "' отсутствует!");
        }
    }

    @Override
    public List<Driver> findAllItems() {
        return jdbcTemplate.queryForStream(
                DriverQueriesPackage.GET_ALL_DRIVERS,
                new DriverMapper()
        ).toList();
    }

    @Override
    public Driver updateItemById(Integer id, Driver item) throws IncorrectBodyException {
        try {
            findItemById(id);
        } catch (NoDataException ex) {
            LoggerController.makeRecord("В таблице 'Drivers' запись с индексом '" + id + "' отсутствует!");
            return createItem(item);
        }

        return jdbcTemplate.queryForObject(
                String.format(DriverQueriesPackage.UPDATE_DRIVER_BY_ID,
                        ((item.getName() != null) ? ("'" + item.getName() + "'") : ("name")),
                        ((item.getAge() != null) ? (item.getAge()) : ("age")),
                        ((item.getPhone() != null) ? ("'" + item.getPhone() + "'") : ("phone")),
                        id),
                new DriverMapper()
        );
    }

    @Override
    public void deleteItemById(Integer id) throws NoDataException {
        findItemById(id);

        jdbcTemplate.execute(String.format(
                "update buses set driver_id = null where driver_id = %d",
                id
        ));

        jdbcTemplate.execute(String.format(DriverQueriesPackage.DELETE_DRIVER_BY_ID, id));
    }

    @Override
    public void deleteAllItems() {
        jdbcTemplate.execute("update buses set driver_id = null");
        jdbcTemplate.execute(DriverQueriesPackage.DELETE_ALL_DRIVERS);
    }

    public List<Driver> getYoungestDrivers(Integer limit) {
        return findAllItems().stream()
                .sorted(Comparator.comparingInt(Driver::getAge))
                .limit(limit)
                .toList();
    }
}
