package bus_app.controllers;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Department;
import bus_app.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus_rep/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Department addDepartment(@RequestBody Department item) throws IncorrectBodyException {

        return repository.createItem(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department getDepartmentById(@PathVariable("id") Integer id) throws NoDataException {

        return repository.findItemById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Department> getAllDepartments() {

        return repository.findAllItems();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Department updateDepartmentById(@PathVariable("id") Integer id,
                             @RequestBody Department item) throws IncorrectBodyException {

        return repository.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDepartmentById(@PathVariable("id") Integer id) throws NoDataException {

        repository.deleteItemById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllDepartments() {

        repository.deleteAllItems();
    }
}
