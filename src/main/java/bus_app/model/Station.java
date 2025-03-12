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
public class Station {

    private Integer id;
    private String name;
    private String district;

    public static void isStationDataCorrect(Station item) throws IncorrectBodyException {
        List<String> noParams = new ArrayList<>();
        if (item.getName() == null) {
            noParams.add("name");
        }
        if (item.getDistrict() == null) {
            noParams.add("district");
        }

        if (!noParams.isEmpty()) {
            throw new IncorrectBodyException("Отсутствуют параметры запроса: " + noParams + "!");
        }
    }
}
