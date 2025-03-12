package bus_app.mappers;

import bus_app.model.Bus;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusMapper implements RowMapper<Bus> {

    @Override
    public Bus mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Bus(
                rs.getInt("id"),
                rs.getString("number"),
                rs.getInt("driver_id"),
                rs.getInt("path_id"),
                rs.getInt("department_id"),
                rs.getInt("seats_number"),
                rs.getString("type")
        );
    }
}
