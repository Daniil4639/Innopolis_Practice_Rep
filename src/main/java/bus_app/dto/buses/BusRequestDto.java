package bus_app.dto.buses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusRequestDto {

    private String number;
    private String path;
    private Long department;
    private List<Long> drivers;
    private Integer seatsNumber;
    private String type;
    private Boolean isActive;
}