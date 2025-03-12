package bus_app.packages;

public class BusQueriesPackage {

    private BusQueriesPackage() {}

    public static final String ADD_BUS = """
            insert into buses
            values (default, %s, %s, %s, %s, %s, %s)
            returning *
            """;

    public static final String GET_BUS_BY_ID = """
            select *
            from buses
            where id = %d
            """;

    public static final String GET_ALL_BUSES = """
            select *
            from buses
            """;

    public static final String UPDATE_BUS_BY_ID = """
            update buses
            set number = %s,
                driver_id = %s,
                path_id = %s,
                department_id = %s,
                seats_number = %s,
                type = %s
            where id = %d
            returning *
            """;

    public static final String DELETE_BUS_BY_ID = """
            delete from buses
            where id = %d
            """;

    public static final String DELETE_ALL_BUSES = """
            truncate buses
            """;

    public static final String GET_STATIONS_BY_BUS_ID = """
            select stations.id, stations.name, stations.district
            from stations
            join path_station on stations.id = path_station.station_id
            join paths on paths.id = path_station.path_id
            join buses on buses.path_id = paths.id
            where buses.id = %d
            order by path_station.time_spent_from_start
            """;
}
