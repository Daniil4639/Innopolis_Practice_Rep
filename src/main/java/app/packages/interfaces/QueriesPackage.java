package app.packages.interfaces;

public interface QueriesPackage {

    String getCreateQuery();

    default String getReadQuery() {
        return "select * from notes where id = ?";
    }

    String getUpdateQuery();

    default String getDeleteQuery() {
        return "delete from notes where id = %d";
    }
}
