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
public class Bus {

    private Integer id;
    private String number;
    private Integer driverId;
    private Integer pathId;
    private Integer departmentId;
    private Integer seatsNumber;
    private String type;

    public static void isBusDataCorrect(Bus item) throws IncorrectBodyException {
        List<String> noParams = new ArrayList<>();
        if (item.getNumber() == null) {
            noParams.add("number");
        }
        if (item.getSeatsNumber() == null) {
            noParams.add("seatsNumber");
        }
        if (item.getType() == null) {
            noParams.add("type");
        }

        if (!noParams.isEmpty()) {
            throw new IncorrectBodyException("Отсутствуют параметры запроса: " + noParams + "!");
        }
    }
}
