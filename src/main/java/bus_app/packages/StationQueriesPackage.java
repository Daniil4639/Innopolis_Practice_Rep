package bus_app.packages;

public class StationQueriesPackage {

    private StationQueriesPackage() {}

    public static final String ADD_STATION = """
            insert into stations
            values (default, %s, %s)
            returning *
            """;

    public static final String GET_STATION_BY_ID = """
            select *
            from stations
            where id = %d
            """;

    public static final String GET_ALL_STATIONS = """
            select *
            from stations
            """;

    public static final String UPDATE_STATION_BY_ID = """
            update stations
            set name = %s,
                district = %s
            where id = %d
            returning *
            """;

    public static final String DELETE_STATION_BY_ID = """
            delete from stations
            where id = %d
            """;

    public static final String DELETE_ALL_STATIONS = """
            delete from stations
            """;
}
