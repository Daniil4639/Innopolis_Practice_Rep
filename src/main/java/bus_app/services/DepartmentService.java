package bus_app.services;

import bus_app.dto.departments.DepartmentDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Department;
import bus_app.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> readAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department addDepartment(DepartmentDto department) {
        try {
            Department newDepartment = new Department(
                    null,
                    department.getName(),
                    department.getAddress(),
                    false
            );

            return departmentRepository.saveAndFlush(newDepartment);
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    public Department updateDepartment(Long id, DepartmentDto department) {
        try {
            Department updatableDepartment = departmentRepository.findById(id).get();

            if (department.getName() != null) {
                updatableDepartment.setName(department.getName());
            }
            if (department.getAddress() != null) {
                updatableDepartment.setAddress(department.getAddress());
            }

            return departmentRepository.saveAndFlush(updatableDepartment);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No department with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    public void deleteDepartment(Long id) {
        try {
            Department department = departmentRepository.findById(id).get();

            departmentRepository.delete(department);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No department with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }
}