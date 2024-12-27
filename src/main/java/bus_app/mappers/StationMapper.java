package bus_app.mappers;

import bus_app.model.Station;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StationMapper implements RowMapper<Station> {

    @Override
    public Station mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Station(
               rs.getInt("id"),
               rs.getString("name"),
               rs.getString("district")
        );
    }
}