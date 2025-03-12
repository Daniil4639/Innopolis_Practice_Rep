package bus_app.mappers;

import bus_app.model.PathStationConnect;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PathStationConnectMapper implements RowMapper<PathStationConnect> {

    @Override
    public PathStationConnect mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PathStationConnect(
                rs.getInt("path_id"),
                rs.getInt("station_id"),
                rs.getInt("time_spent_from_start")
        );
    }
}
