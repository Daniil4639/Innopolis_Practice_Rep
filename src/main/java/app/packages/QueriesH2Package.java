package app.packages;

import app.packages.interfaces.QueriesPackage;

public class QueriesH2Package implements QueriesPackage {

    @Override
    public String getCreateQuery() {
        return "select * from final table (insert into notes values(default, '%s', '%s', '%s'))";
    }

    @Override
    public String getUpdateQuery() {
        return "select * from final table (update notes set date_and_time = %s, topic = %s, text = %s where id = %d)";
    }
}
