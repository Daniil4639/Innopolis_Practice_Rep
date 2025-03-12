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
public class Department {

    private Integer id;
    private String name;
    private String address;

    public static void isDepartmentDataCorrect(Department item) throws IncorrectBodyException {
        List<String> noParams = new ArrayList<>();
        if (item.getName() == null) {
            noParams.add("name");
        }
        if (item.getAddress() == null) {
            noParams.add("address");
        }

        if (!noParams.isEmpty()) {
            throw new IncorrectBodyException("Отсутствуют параметры запроса: " + noParams + "!");
        }
    }
}
