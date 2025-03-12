package bus_app.controllers;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.*;
import bus_app.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bus_rep/buses")
public class BusController {

    @Autowired
    private BusRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Bus addBus(@RequestBody Bus item) throws IncorrectBodyException {

        return repository.createItem(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bus getBusById(@PathVariable("id") Integer id) throws NoDataException {

        return repository.findItemById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Bus> getAllBuses() {

        return repository.findAllItems();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bus updateBusById(@PathVariable("id") Integer id,
                             @RequestBody Bus item) throws IncorrectBodyException {

        return repository.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBusById(@PathVariable("id") Integer id) throws NoDataException {

        repository.deleteItemById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllBuses() {

        repository.deleteAllItems();
    }

    @GetMapping("/{id}/get_stations")
    @ResponseStatus(HttpStatus.OK)
    public List<Station> getStationsInOrderByBusNumber(@PathVariable("id") Integer id) {

        List<Station> res = repository.getStationsByBusNumber(id);
        System.out.println(res);

        return res;
    }

    @GetMapping("/group_by/path")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, List<Bus>> getBusesGroupsByPathId() {

        return repository.getBusesGroupsByPathId();
    }
}