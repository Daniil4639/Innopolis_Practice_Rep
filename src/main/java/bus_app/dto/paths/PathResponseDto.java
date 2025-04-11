package bus_app.dto.paths;

import bus_app.dto.stations.StationDto;
import bus_app.models.Path;
import bus_app.models.PathStation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PathResponseDto {

    private String number;
    private StationDto beginStation;
    private StationDto endStation;
    private List<StationDto> stations;
    private Integer duration;

    public PathResponseDto(Path path) {
        this.number = path.getNumber();
        this.beginStation = new StationDto(path.getBeginStation());
        this.endStation = new StationDto(path.getEndStation());
        this.stations = path.getStations().stream().map(PathStation::getStation).map(StationDto::new).toList();
        this.duration = path.getDuration();
    }
}