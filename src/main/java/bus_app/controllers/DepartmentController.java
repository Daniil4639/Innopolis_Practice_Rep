package bus_app.controllers;

import bus_app.model.Department;
import bus_app.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus_rep/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository repository;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Department insertNewDepartment(@RequestParam("name") String name,
                                          @RequestParam("address") String address) {

        Department res = repository.insertNewDepartment(name, address);
        System.out.println(res);

        return res;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDepartmentById(@PathVariable("id") Integer id) {
        repository.deleteDepartmentById(id);
    }
}
