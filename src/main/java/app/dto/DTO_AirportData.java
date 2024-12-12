package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTO_AirportData {

    private String airportCode;
    private String airportName;
    private String city;
    private String coordinates;
    private String timezone;

    @Override
    public String toString() {
        return String.format("[code: %s; name: %s; city: %s; coordinates: %s; zone: %s]",
                airportCode,
                airportName,
                city,
                coordinates,
                timezone);
    }
}
