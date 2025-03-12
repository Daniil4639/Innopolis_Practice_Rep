package bus_app.packages;

public class PathQueriesPackage {

    private PathQueriesPackage() {}

    public static final String ADD_PATH = """
            insert into paths
            values (default, %d, %d, %d)
            returning *
            """;

    public static final String GET_PATH_BY_ID = """
            select *
            from paths
            where id = %d
            """;

    public static final String GET_ALL_PATHS = """
            select *
            from paths
            """;

    public static final String UPDATE_PATH_BY_ID = """
            update paths
            set begin_station = %s,
                end_station = %s,
                duration = %s
            where id = %d
            returning *
            """;

    public static final String DELETE_PATH_BY_ID = """
            delete from paths
            where id = %d
            """;

    public static final String DELETE_ALL_PATHS = """
            delete from paths
            """;

    public static final String GET_DRIVER_BY_PATH = """
            with path_info as (select paths.id,
            (select stations.name from stations where stations.id = paths.begin_station) as begin,
            (select stations.name from stations where stations.id = paths.end_station) as end
            from paths
            )
            select drivers.id, drivers.name, drivers.age, drivers.phone
            from path_info
            join buses on buses.path_id = path_info.id
            join drivers on drivers.id = buses.driver_id
            where path_info.begin = '%s' and path_info.end = '%s'
            """;
}
