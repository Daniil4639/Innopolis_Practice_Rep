package bus_app.repositories;

import bus_app.mappers.BusMapper;
import bus_app.mappers.StationMapper;
import bus_app.model.*;
import bus_app.packages.QueriesPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BusRepository{

    private final JdbcTemplate jdbcTemplate;

    public Bus getBusByNum(Integer id) {
        return jdbcTemplate.queryForObject(
                String.format(QueriesPackage.GET_BUS_BY_ID, id),
                new BusMapper()
        );
    }

    public List<Station> getStationsByBusNumber(Integer id) {
        return jdbcTemplate.queryForStream(
                String.format(QueriesPackage.GET_STATIONS_BY_BUS_ID, id),
                new StationMapper()).toList();
    }

    public Bus updateBusById(Bus obj) {
        jdbcTemplate.execute(
                String.format(QueriesPackage.UPDATE_BUS_INFO,
                        ((obj.getNumber() != null) ? ("'" + obj.getNumber() + "'") : ("number")),
                        ((obj.getDriverId() != null) ? (obj.getDriverId()) : ("driver_id")),
                        ((obj.getPathId() != null) ? (obj.getPathId()) : ("path_id")),
                        ((obj.getDepartmentId() != null) ? (obj.getDepartmentId()) : ("department_id")),
                        ((obj.getSeatsNumber() != null) ? (obj.getSeatsNumber()) : ("seats_number")),
                        ((obj.getType() != null) ? ("'" + obj.getType() + "'") : ("type")),
                        obj.getId())
        );

        return jdbcTemplate.queryForObject(
                String.format("select * from bus_db.buses where id = %d", obj.getId()),
                new BusMapper()
        );
    }
}