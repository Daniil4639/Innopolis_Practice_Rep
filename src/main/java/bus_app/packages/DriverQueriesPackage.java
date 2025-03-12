package bus_app.packages;

public class DriverQueriesPackage {

    private DriverQueriesPackage() {}

    public static final String ADD_DRIVER = """
            insert into drivers
            values (default, %s, %d, %s)
            returning *
            """;

    public static final String GET_DRIVER_BY_ID = """
            select *
            from drivers
            where id = %d
            """;

    public static final String GET_ALL_DRIVERS = """
            select *
            from drivers
            """;

    public static final String UPDATE_DRIVER_BY_ID = """
            update drivers
            set name = %s,
                age = %s,
                phone = %s
            where id = %d
            returning *
            """;

    public static final String DELETE_DRIVER_BY_ID = """
            delete from drivers
            where id = %d
            """;

    public static final String DELETE_ALL_DRIVERS = """
            delete from drivers
            """;
}
