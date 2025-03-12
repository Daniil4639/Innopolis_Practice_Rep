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
public class Path {

    private Integer id;
    private Integer beginStation;
    private Integer endStation;
    private Integer duration;

    public static void isPathDataCorrect(Path item) throws IncorrectBodyException {
        List<String> noParams = new ArrayList<>();
        if (item.getBeginStation() == null) {
            noParams.add("beginStation");
        }
        if (item.getEndStation() == null) {
            noParams.add("endStation");
        }
        if (item.getDuration() == null) {
            noParams.add("duration");
        }

        if (!noParams.isEmpty()) {
            throw new IncorrectBodyException("Отсутствуют параметры запроса: " + noParams + "!");
        }
    }
}
