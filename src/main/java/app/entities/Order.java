package app.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;
    private Integer itemNumber;
    private Integer amount;
    private Integer sumCost;
    private LocalDateTime dateAndTime;

    public static void OrderInfoIsCorrect(Order order) throws IllegalArgumentException {
        if (order.getItemNumber() == null || order.getAmount() == null ||
                order.getSumCost() == null || order.getAmount() <= 0 || order.getSumCost() <= 0) {
            throw new IllegalArgumentException("Wrong request body!");
        }
    }
}
