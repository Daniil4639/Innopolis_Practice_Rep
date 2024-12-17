package bus_app.controllers;

import bus_app.model.Driver;
import bus_app.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bus_rep/drivers")
public class DriverController {

    @Autowired
    private DriverRepository repository;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Driver updateDriverInfo(@PathVariable("id") Integer id,
                                   @RequestParam("name") Optional<String> name,
                                   @RequestParam("age") Optional<Integer> age,
                                   @RequestParam("phone") Optional<String> phone) {

        Driver res = repository.updateDriverInfo(
                new Driver(id, name.orElse(null), age.orElse(null), phone.orElse(null)));
        System.out.println(res);

        return res;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Driver deleteDriverById(@PathVariable("id") Integer id) {
        Driver res = repository.deleteDriverById(id);
        System.out.println(res);

        return res;
    }
}
