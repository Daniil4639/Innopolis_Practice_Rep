package bus_app.repositories;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.mappers.BusMapper;
import bus_app.mappers.StationMapper;
import bus_app.model.*;
import bus_app.packages.BusQueriesPackage;
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
public class BusRepository implements DefaultDBRepository<Bus> {

    private final JdbcTemplate jdbcTemplate;
    private final DepartmentRepository departmentRepository;
    private final DriverRepository driverRepository;
    private final PathRepository pathRepository;

    @Override
    public Bus createItem(Bus item) throws IncorrectBodyException {
        Bus.isBusDataCorrect(item);
        isIdsCorrect(item);

        return jdbcTemplate.queryForObject(
            String.format(BusQueriesPackage.ADD_BUS,
                    ("'" + item.getNumber() + "'"),
                    ((item.getDriverId() != null) ? (item.getDriverId()) : ("null")),
                    ((item.getPathId() != null) ? (item.getPathId()) : ("null")),
                    ((item.getDepartmentId() != null) ? (item.getDepartmentId()) : ("null")),
                    (item.getSeatsNumber()),
                    ("'" + item.getType() + "'")),
                new BusMapper()
        );
    }

    @Override
    public Bus findItemById(Integer id) throws NoDataException {
        try {
            return jdbcTemplate.queryForObject(
                    String.format(BusQueriesPackage.GET_BUS_BY_ID, id),
                    new BusMapper()
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("Запись в таблице 'Buses' с идентификатором '" +
                    id + "' отсутствует!");
        }
    }

    @Override
    public List<Bus> findAllItems() {
        return jdbcTemplate.queryForStream(
                BusQueriesPackage.GET_ALL_BUSES,
                new BusMapper()
        ).toList();
    }

    @Override
    public Bus updateItemById(Integer id, Bus item) throws IncorrectBodyException {
        isIdsCorrect(item);

        try {
            findItemById(id);
        } catch (NoDataException ex) {
            LoggerController.makeRecord("В таблице 'Buses' запись с индексом '" + id + "' отсутствует!");
            return createItem(item);
        }

        return jdbcTemplate.queryForObject(
                String.format(BusQueriesPackage.UPDATE_BUS_BY_ID,
                        ((item.getNumber() != null) ? ("'" + item.getNumber() + "'") : ("number")),
                        ((item.getDriverId() != null) ? (item.getDriverId()) : ("driver_id")),
                        ((item.getPathId() != null) ? (item.getPathId()) : ("path_id")),
                        ((item.getDepartmentId() != null) ? (item.getDepartmentId()) : ("department_id")),
                        ((item.getSeatsNumber() != null) ? (item.getSeatsNumber()) : ("seats_number")),
                        ((item.getType() != null) ? ("'" + item.getType() + "'") : ("type")),
                        id),
                new BusMapper()
        );
    }

    @Override
    public void deleteItemById(Integer id) throws NoDataException {
        findItemById(id);

        jdbcTemplate.execute(String.format(BusQueriesPackage.DELETE_BUS_BY_ID, id));
    }

    @Override
    public void deleteAllItems() {
        jdbcTemplate.execute(BusQueriesPackage.DELETE_ALL_BUSES);
    }

    public List<Station> getStationsByBusNumber(Integer id) {
        return jdbcTemplate.queryForStream(
                String.format(BusQueriesPackage.GET_STATIONS_BY_BUS_ID, id),
                new StationMapper())
                .toList();
    }

    public Map<Integer, List<Bus>> getBusesGroupsByPathId() {
        return findAllItems().stream()
                .collect(Collectors.groupingBy(Bus::getPathId));
    }

    private void isIdsCorrect(Bus item) throws IncorrectBodyException {
        if (item.getDepartmentId() != null) {
            try {
                departmentRepository.findItemById(item.getDepartmentId());
            } catch (NoDataException ex) {
                throw new IncorrectBodyException("В таблице 'Departments' отсутствует " +
                        "запись с идентификатором '" + item.getDepartmentId() + "'!");
            }
        }

        if (item.getDriverId() != null) {
            try {
                driverRepository.findItemById(item.getDriverId());
            } catch (NoDataException ex) {
                throw new IncorrectBodyException("В таблице 'Drivers' отсутствует " +
                        "запись с идентификатором '" + item.getDriverId() + "'!");
            }
        }

        if (item.getPathId() != null) {
            try {
                pathRepository.findItemById(item.getPathId());
            } catch (NoDataException ex) {
                throw new IncorrectBodyException("В таблице 'Paths' отсутствует " +
                        "запись с идентификатором '" + item.getPathId() + "'!");
            }
        }
    }
}