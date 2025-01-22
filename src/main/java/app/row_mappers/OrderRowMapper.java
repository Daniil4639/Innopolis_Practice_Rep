package app.row_mappers;

import app.entities.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Order(
                rs.getInt("id"),
                rs.getInt("item_number"),
                rs.getInt("amount"),
                rs.getInt("sum_cost"),
                rs.getTimestamp("date_and_time").toLocalDateTime()
        );
    }
}
