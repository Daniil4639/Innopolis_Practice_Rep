package bus_app.model;

import lombok.*;

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
}
