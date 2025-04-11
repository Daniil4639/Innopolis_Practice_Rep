package bus_app.dto.buses;

import bus_app.dto.departments.DepartmentDto;
import bus_app.dto.drivers.DriverDto;
import bus_app.dto.paths.PathResponseDto;
import bus_app.models.Bus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusResponseDto {

    private String number;
    private PathResponseDto path;
    private DepartmentDto department;
    private List<DriverDto> drivers;
    private Integer seatsNumber;
    private String type;
    private Boolean isActive;

    public BusResponseDto(Bus bus) {
        this.number = bus.getNumber();
        this.path = new PathResponseDto(bus.getPath());
        this.department = new DepartmentDto(bus.getDepartment());
        this.drivers = bus.getDrivers().stream().map(DriverDto::new).toList();
        this.seatsNumber = bus.getSeatsNumber();
        this.isActive = bus.getIsActive();
    }
}