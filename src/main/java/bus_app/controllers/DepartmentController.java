package bus_app.controllers;

import bus_app.dto.departments.DepartmentDto;
import bus_app.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.readAllDepartments().stream()
                .map(DepartmentDto::new).toList();
    }

    @PostMapping
    public DepartmentDto addDepartment(@RequestBody DepartmentDto department) {
        return new DepartmentDto(departmentService.addDepartment(department));
    }

    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(@PathVariable("id") Long id, @RequestBody DepartmentDto department) {
        return new DepartmentDto(departmentService.updateDepartment(id, department));
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
    }
}