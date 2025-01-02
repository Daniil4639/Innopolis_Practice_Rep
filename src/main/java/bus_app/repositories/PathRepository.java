package bus_app.repositories;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.mappers.DriverMapper;
import bus_app.mappers.PathMapper;
import bus_app.mappers.PathStationConnectMapper;
import bus_app.model.Driver;
import bus_app.model.Path;
import bus_app.model.PathStationConnect;
import bus_app.packages.PathQueriesPackage;
import bus_app.packages.PathStationConnectQueriesPackage;
import bus_app.repositories.interfaces.DefaultDBRepository;
import bus_app.utils.LoggerController;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PathRepository implements DefaultDBRepository<Path> {

    private final JdbcTemplate jdbcTemplate;
    private final StationRepository stationRepository;

    @Override
    public Path createItem(Path item) throws IncorrectBodyException {
        Path.isPathDataCorrect(item);
        isIdsCorrect(item);

        return jdbcTemplate.queryForObject(
                String.format(PathQueriesPackage.ADD_PATH,
                        (item.getBeginStation()),
                        (item.getEndStation()),
                        (item.getDuration())),
                new PathMapper()
        );
    }

    @Override
    public Path findItemById(Integer id) throws NoDataException {
        try {
            return jdbcTemplate.queryForObject(
                    String.format(PathQueriesPackage.GET_PATH_BY_ID, id),
                    new PathMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("Запись в таблице 'Paths' с идентификатором '" +
                    id + "' отсутствует!");
        }
    }

    @Override
    public List<Path> findAllItems() {
        return jdbcTemplate.queryForStream(
                PathQueriesPackage.GET_ALL_PATHS,
                new PathMapper()
        ).toList();
    }

    @Override
    public Path updateItemById(Integer id, Path item) throws IncorrectBodyException {
        isIdsCorrect(item);

        try {
            findItemById(id);
        } catch (NoDataException ex) {
            LoggerController.makeRecord("В таблице 'Paths' запись с индексом '" + id + "' отсутствует!");
            return createItem(item);
        }

        return jdbcTemplate.queryForObject(
                String.format(PathQueriesPackage.UPDATE_PATH_BY_ID,
                        ((item.getBeginStation() != null) ? (item.getBeginStation()) : ("begin_station")),
                        ((item.getEndStation() != null) ? (item.getEndStation()) : ("end_station")),
                        ((item.getDuration() != null) ? (item.getDuration()) : ("duration")),
                        id),
                new PathMapper()
        );
    }

    @Override
    public void deleteItemById(Integer id) throws NoDataException {
        findItemById(id);

        jdbcTemplate.execute(String.format(
                "update buses set path_id = null where path_id = %d",
                id
        ));

        jdbcTemplate.execute(String.format(
                "delete from path_station where path_id = %d",
                id
        ));

        jdbcTemplate.execute(String.format(PathQueriesPackage.DELETE_PATH_BY_ID, id));
    }

    @Override
    public void deleteAllItems() {
        jdbcTemplate.execute("update buses set path_id = null");
        jdbcTemplate.execute("truncate path_station");
        jdbcTemplate.execute(PathQueriesPackage.DELETE_ALL_PATHS);
    }

    public Driver getDriverByPath(String pathBegin, String pathEnd) {
        return jdbcTemplate.queryForObject(
                String.format(PathQueriesPackage.GET_DRIVER_BY_PATH, pathBegin, pathEnd),
                new DriverMapper()
        );
    }

    public Map<Integer, List<Integer>> getStationGroupsByPath() {
        return jdbcTemplate.queryForStream(
                PathStationConnectQueriesPackage.GET_ALL_PATH_STATION_CONNECTIONS,
                new PathStationConnectMapper()
        ).collect(Collectors.groupingBy(PathStationConnect::getPathId,
                Collectors.collectingAndThen(Collectors.toList(),
                        item ->
                                item.stream().map(PathStationConnect::getStationId).toList())));
    }

    private void isIdsCorrect(Path item) throws IncorrectBodyException {
        if (item.getBeginStation() != null) {
            try {
                stationRepository.findItemById(item.getBeginStation());
            } catch (NoDataException ex) {
                throw new IncorrectBodyException("В таблице 'Stations' отсутствует " +
                        "запись с идентификатором '" + item.getBeginStation() + "'!");
            }
        }

        if (item.getEndStation() != null) {
            try {
                stationRepository.findItemById(item.getEndStation());
            } catch (NoDataException ex) {
                throw new IncorrectBodyException("В таблице 'Stations' отсутствует " +
                        "запись с идентификатором '" + item.getEndStation() + "'!");
            }
        }
    }
}
