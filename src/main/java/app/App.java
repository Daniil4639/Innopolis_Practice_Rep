package app;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
                new Product(1, "tomato", 3200L, 100),
                new Product(2, "apple", 520L, 25),
                new Product(3, "bread", 880L, 47)
        ));

        System.out.println(products);
    }
}