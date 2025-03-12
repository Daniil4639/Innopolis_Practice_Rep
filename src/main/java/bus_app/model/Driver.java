package bus_app.model;

import bus_app.exceptions.IncorrectBodyException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Driver {

    private Integer id;
    private String name;
    private Integer age;
    private String phone;

    public static void isDriverDataCorrect(Driver item) throws IncorrectBodyException {
        List<String> noParams = new ArrayList<>();
        if (item.getName() == null) {
            noParams.add("name");
        }
        if (item.getAge() == null) {
            noParams.add("age");
        }
        if (item.getPhone() == null) {
            noParams.add("phone");
        }

        if (!noParams.isEmpty()) {
            throw new IncorrectBodyException("Отсутствуют параметры запроса: " + noParams + "!");
        }
    }
}
