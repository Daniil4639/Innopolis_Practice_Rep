package bus_app.repositories;

import bus_app.model.Driver;
import bus_app.model.Path;
import bus_app.packages.QueriesPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PathRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StationRepository stationRepository;

    public Path insertNewPath(String begin, String end, Integer duration) {
        Integer maxId = jdbcTemplate.queryForObject(
                "select max(id) from bus_db.paths",
                Integer.class
        );

        jdbcTemplate.execute(
                String.format(QueriesPackage.INSERT_NEW_PATH,
                        maxId + 1, stationRepository.getStationByName(begin).getId(),
                        stationRepository.getStationByName(end).getId(), duration)
        );

        return jdbcTemplate.queryForObject(
                String.format("select * from bus_db.paths where id = %d", maxId + 1),
                BeanPropertyRowMapper.newInstance(Path.class)
        );
    }

    public Driver getDriverByPath(String pathBegin, String pathEnd) {
        return jdbcTemplate.queryForObject(
                String.format(QueriesPackage.GET_DRIVER_BY_PATH, pathBegin, pathEnd),
                BeanPropertyRowMapper.newInstance(Driver.class)
        );
    }
}
