package bus_app.repositories;

import bus_app.model.Station;
import bus_app.packages.QueriesPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StationRepository {

    private final JdbcTemplate jdbcTemplate;

    public Station getStationByName(String name) {
        return jdbcTemplate.queryForObject(
                String.format(QueriesPackage.GET_STATION_BY_NAME, name),
                BeanPropertyRowMapper.newInstance(Station.class)
        );
    }

    public Station insertNewStation(String name, String district, Integer[] pathIds, Integer[] durations) {
        Integer maxId = jdbcTemplate.queryForObject(
                "select max(id) from bus_db.stations",
                Integer.class
        );

        jdbcTemplate.execute(
                String.format(QueriesPackage.INSERT_NEW_STATION, maxId + 1, name, district)
        );

        for (int i = 0; i < Math.min(pathIds.length, durations.length); i++) {
            jdbcTemplate.execute(
                    String.format(QueriesPackage.ADD_CONNECTION_STATION_AND_PATH,
                            pathIds[i], maxId + 1, durations[i])
            );
        }

        return jdbcTemplate.queryForObject(
                String.format("select * from bus_db.stations where id = %d", maxId + 1),
                BeanPropertyRowMapper.newInstance(Station.class)
        );
    }
}
