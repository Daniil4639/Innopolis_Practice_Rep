package app.packages;

import app.packages.interfaces.QueriesPackage;

public class QueriesPostgresPackage implements QueriesPackage {

    @Override
    public String getCreateQuery() {
        return "insert into notes values(default, '%s', '%s', '%s') returning *";
    }

    @Override
    public String getUpdateQuery() {
        return "update notes set date_and_time = %s, topic = %s, text = %s where id = %d returning *";
    }
}
