package bus_app.dto.departments;

import bus_app.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private String name;
    private String address;

    public DepartmentDto(Department department) {
        this.name = department.getName();
        this.address = department.getAddress();
    }
}