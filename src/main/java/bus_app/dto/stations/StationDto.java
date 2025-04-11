package bus_app.dto.stations;

import bus_app.models.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

    private String name;
    private String district;

    public StationDto(Station station) {
        this.name = station.getName();
        this.district = station.getDistrict();
    }
}