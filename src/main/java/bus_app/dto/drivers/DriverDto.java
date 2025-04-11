package bus_app.dto.drivers;

import bus_app.models.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private String name;
    private Integer age;
    private String phone;

    public DriverDto(Driver driver) {
        this.name = driver.getName();
        this.age = driver.getAge();
        this.phone = driver.getPhone();
    }
}