package bus_app.repositories;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.mappers.PathStationConnectMapper;
import bus_app.mappers.StationMapper;
import bus_app.model.PathStationConnect;
import bus_app.model.Station;
import bus_app.packages.PathStationConnectQueriesPackage;
import bus_app.packages.StationQueriesPackage;
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
public class StationRepository implements DefaultDBRepository<Station> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Station createItem(Station item) throws IncorrectBodyException {
        Station.isStationDataCorrect(item);

        return jdbcTemplate.queryForObject(
                String.format(StationQueriesPackage.ADD_STATION,
                        ("'" + item.getName() + "'"),
                        ("'" + item.getDistrict() + "'")),
                new StationMapper()
        );
    }

    @Override
    public Station findItemById(Integer id) throws NoDataException {
        try {
            return jdbcTemplate.queryForObject(
                    String.format(StationQueriesPackage.GET_STATION_BY_ID, id),
                    new StationMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("Запись в таблице 'Stations' с идентификатором '" +
                    id + "' отсутствует!");
        }
    }

    @Override
    public List<Station> findAllItems() {
        return jdbcTemplate.queryForStream(
                StationQueriesPackage.GET_ALL_STATIONS,
                new StationMapper()
        ).toList();
    }

    @Override
    public Station updateItemById(Integer id, Station item) throws IncorrectBodyException {
        try {
            findItemById(id);
        } catch (NoDataException ex) {
            LoggerController.makeRecord("В таблице 'Stations' запись с индексом '" + id + "' отсутствует!");
            return createItem(item);
        }

        return jdbcTemplate.queryForObject(
                String.format(StationQueriesPackage.UPDATE_STATION_BY_ID,
                        ((item.getName() != null) ? ("'" + item.getName() + "'") : ("name")),
                        ((item.getDistrict() != null) ? ("'" + item.getDistrict() + "'") : ("district")),
                        id),
                new StationMapper()
        );
    }

    @Override
    public void deleteItemById(Integer id) throws NoDataException {
        findItemById(id);

        jdbcTemplate.execute(String.format(
                "update paths set begin_station = null where begin_station = %d",
                id
        ));

        jdbcTemplate.execute(String.format(
                "update paths set end_station = null where end_station = %d",
                id
        ));

        jdbcTemplate.execute(String.format(
                "delete from path_station where station_id = %d",
                id
        ));

        jdbcTemplate.execute(String.format(StationQueriesPackage.DELETE_STATION_BY_ID, id));
    }

    @Override
    public void deleteAllItems() {
        jdbcTemplate.execute("update paths set begin_station = null, end_station = null");
        jdbcTemplate.execute("truncate path_station");
        jdbcTemplate.execute(StationQueriesPackage.DELETE_ALL_STATIONS);
    }

    public List<Integer> getStationsInOrderByPathId(Integer id) {
        return jdbcTemplate.queryForStream(
                PathStationConnectQueriesPackage.GET_ALL_PATH_STATION_CONNECTIONS,
                new PathStationConnectMapper()
        ).filter(elem -> elem.getPathId().equals(id))
                .sorted(Comparator.comparingInt(PathStationConnect::getTimeSpentFromStart))
                .map(PathStationConnect::getStationId)
                .toList();
    }
}
