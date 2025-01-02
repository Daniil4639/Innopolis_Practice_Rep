package bus_app.mappers;

import bus_app.model.Driver;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverMapper implements RowMapper<Driver> {

    @Override
    public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Driver(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("phone")
        );
    }
}
