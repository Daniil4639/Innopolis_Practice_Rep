package app.actuator;

import app.repositories.OrderRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    public CustomMetrics(OrderRepository repository, MeterRegistry registry) {
        Gauge.builder("count_of_orders",
                        repository::findOrdersCount)
                .register(registry);

        Gauge.builder("avg_cost_of_orders",
                repository::findAverageOrderCost)
                .register(registry);
    }
}
