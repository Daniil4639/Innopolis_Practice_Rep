package app.controllers;

import app.entities.Order;
import app.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrder(@PathVariable("id") Integer id) {
        return repository.findOrder(id);
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public Integer getOrdersCount() {
        return repository.findOrdersCount();
    }

    @GetMapping("/avg_cost")
    @ResponseStatus(HttpStatus.OK)
    public Integer getAverageCost() {
        return repository.findAverageOrderCost();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void postOrder(@RequestBody Order order) throws IllegalArgumentException {
        repository.createOrder(order);
    }
}
