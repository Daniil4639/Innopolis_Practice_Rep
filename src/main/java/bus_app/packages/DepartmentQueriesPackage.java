package bus_app.packages;

public class DepartmentQueriesPackage {

    private DepartmentQueriesPackage() {}

    public static final String ADD_DEPARTMENT = """
            insert into departments
            values (default, %s, %s)
            returning *
            """;

    public static final String GET_DEPARTMENT_BY_ID = """
            select *
            from departments
            where id = %d
            """;

    public static final String GET_ALL_DEPARTMENTS = """
            select *
            from departments
            """;

    public static final String UPDATE_DEPARTMENT_BY_ID = """
            update departments
            set name = %s,
                address = %s
            where id = %d
            returning *
            """;

    public static final String DELETE_DEPARTMENT_BY_ID = """
            delete from departments
            where id = %d
            """;

    public static final String DELETE_ALL_DEPARTMENTS = """
            delete from departments
            """;
}
