package bus_app.repositories;

import bus_app.mappers.DriverMapper;
import bus_app.model.Driver;
import bus_app.packages.QueriesPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DriverRepository {

    private final JdbcTemplate jdbcTemplate;

    public Driver updateDriverInfo(Driver obj) {
        jdbcTemplate.execute(
                String.format(QueriesPackage.UPDATE_DRIVER_INFO,
                        ((obj.getName() != null) ? ("'" + obj.getName() + "'") : ("name")),
                        ((obj.getAge() != null) ? (obj.getAge()) : ("age")),
                        ((obj.getPhone() != null) ? ("'" + obj.getPhone() + "'") : ("phone")),
                        obj.getId())
        );

        return jdbcTemplate.queryForObject(
                String.format("select * from bus_db.drivers where id = %d", obj.getId()),
                new DriverMapper()
        );
    }

    public Driver deleteDriverById(Integer id) {
        Driver driver = jdbcTemplate.queryForObject(
                String.format("select * from bus_db.drivers where id = %d", id),
                new DriverMapper()
        );

        jdbcTemplate.execute(
                String.format(QueriesPackage.DELETE_DRIVER_BY_ID, id)
        );

        return driver;
    }
}
