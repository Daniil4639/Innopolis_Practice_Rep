package app;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    private String description;
    private Long cost;
    private Integer amount;
}
