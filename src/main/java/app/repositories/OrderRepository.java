package app.repositories;

import app.entities.Order;
import app.row_mappers.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JdbcTemplate template;

    public Order findOrder(Integer id) {
        return template.queryForObject(
                "select * from orders where id = ?",
                new OrderRowMapper(),
                id
        );
    }

    public Integer findOrdersCount() {
        return template.queryForObject(
                "select count(*) from orders",
                Integer.class
        );
    }

    public Integer findAverageOrderCost() {
        return template.queryForObject(
                "select avg(sum_cost) from orders",
                Integer.class
        );
    }

    public void createOrder(Order order) throws IllegalArgumentException {
        Order.OrderInfoIsCorrect(order);

        template.execute(
                String.format("insert into orders values (default, %d, %d, %d, '%s')",
                        order.getItemNumber(), order.getAmount(), order.getSumCost(),
                        order.getDateAndTime().toString())
        );
    }
}
