package bus_app.mappers;

import bus_app.model.Path;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PathMapper implements RowMapper<Path> {

    @Override
    public Path mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Path(
                rs.getInt("id"),
                rs.getInt("begin_station"),
                rs.getInt("end_station"),
                rs.getInt("duration")
        );
    }
}