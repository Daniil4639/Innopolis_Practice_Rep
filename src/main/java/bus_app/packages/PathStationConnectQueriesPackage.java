package bus_app.packages;

public class PathStationConnectQueriesPackage {

    private PathStationConnectQueriesPackage() {}

    public static final String GET_ALL_PATH_STATION_CONNECTIONS = """
            select *
            from path_station
            """;
}
