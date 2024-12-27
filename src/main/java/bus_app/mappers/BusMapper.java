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
                rs.getInt("driverId"),
                rs.getInt("pathId"),
                rs.getInt("departmentId"),
                rs.getInt("seatsNumber"),
                rs.getString("type")
        );
    }
}
