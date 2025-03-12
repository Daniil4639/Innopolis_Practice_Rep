package bus_app.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PathStationConnect {

    private Integer pathId;
    private Integer stationId;
    private Integer timeSpentFromStart;
}
