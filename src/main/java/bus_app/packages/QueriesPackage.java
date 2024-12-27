package bus_app.packages;

public class QueriesPackage {

    private QueriesPackage() {}

    public static final String GET_BUS_BY_ID = """
            select *
            from bus_db.buses
            where id = %d
            """;

    public static final String GET_STATIONS_BY_BUS_ID = """
            select bus_db.stations.id, bus_db.stations.name, bus_db.stations.district
            from bus_db.stations
            join bus_db.path_station on bus_db.stations.id = bus_db.path_station.station_id
            join bus_db.paths on bus_db.paths.id = bus_db.path_station.path_id
            join bus_db.buses on bus_db.buses.path_id = bus_db.paths.id
            where bus_db.buses.id = %d
            order by bus_db.path_station.time_spent_from_start
            """;

    public static final String UPDATE_BUS_INFO = """
            update bus_db.buses
            set number = %s,
                driver_id = %s,
                path_id = %s,
                department_id = %s,
                seats_number = %s,
                type = %s
            where id = %d
            """;

    public static final String GET_DRIVER_BY_PATH = """
            with path_info as (select bus_db.paths.id,
            (select bus_db.stations.name from bus_db.stations where bus_db.stations.id = bus_db.paths.begin_station) as begin,
            (select bus_db.stations.name from bus_db.stations where bus_db.stations.id = bus_db.paths.end_station) as end
            from bus_db.paths
            )
            select bus_db.drivers.id, bus_db.drivers.name, bus_db.drivers.age, bus_db.drivers.phone
            from path_info
            join bus_db.buses on bus_db.buses.path_id = path_info.id
            join bus_db.drivers on bus_db.drivers.id = bus_db.buses.driver_id
            where path_info.begin = '%s' and path_info.end = '%s'
            """;

    public static final String UPDATE_DRIVER_INFO = """
            update bus_db.drivers
            set name = %s,
                age = %s,
                phone = %s
            where id = %d
            """;

    public static final String GET_STATION_BY_NAME = """
            select *
            from bus_db.stations
            where name = '%s'
            """;

    public static final String INSERT_NEW_PATH = """
            insert into bus_db.paths
            values (%d, %d, %d, %d)
            """;

    public static final String INSERT_NEW_DEPARTMENT = """
            insert into bus_db.departments
            values (%d, %s, %s)
            """;

    public static final String DELETE_DRIVER_BY_ID = """
            delete from bus_db.drivers
            where id = %d
            """;

    public static final String DELETE_DEPARTMENT_BY_ID = """
            delete from bus_db.buses
            where department_id = %d;
            delete from bus_db.departments
            where id = %d
            """;

    public static final String INSERT_NEW_STATION = """
            insert into bus_db.stations
            values (%d, '%s', '%s')
            """;

    public static final String ADD_CONNECTION_STATION_AND_PATH = """
            insert into bus_db.path_station
            values (%d, %d, %d)
            """;
}