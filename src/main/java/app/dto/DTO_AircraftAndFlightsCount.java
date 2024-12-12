package app.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTO_AircraftAndFlightsCount {

    private String code;
    private Integer flightsCount;

    @Override
    public String toString() {
        return String.format("[aircraftName: %s; flightsCount: %d]",
                code,
                flightsCount);
    }
}
