package bus_app.dto.paths;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PathRequestDto {

    private String number;
    private Long beginStation;
    private Long endStation;
    private List<Long> stations;
    private List<Long> timesFromStartToStations;
    private Integer duration;
}