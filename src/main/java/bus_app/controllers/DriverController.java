package bus_app.controllers;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Driver;
import bus_app.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus_rep/drivers")
public class DriverController {

    @Autowired
    private DriverRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Driver addDriver(@RequestBody Driver item) throws IncorrectBodyException {

        return repository.createItem(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Driver getDriverById(@PathVariable("id") Integer id) throws NoDataException {

        return repository.findItemById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Driver> getAllDrivers() {

        return repository.findAllItems();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Driver updateDriverById(@PathVariable("id") Integer id,
                                           @RequestBody Driver item) throws IncorrectBodyException {

        return repository.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDriverById(@PathVariable("id") Integer id) throws NoDataException {

        repository.deleteItemById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllDrivers() {

        repository.deleteAllItems();
    }

    @GetMapping("/order/age")
    @ResponseStatus(HttpStatus.OK)
    public List<Driver> getYoungestDrivers(@RequestParam("limit") Integer limit) {

        return repository.getYoungestDrivers(limit);
    }
}
