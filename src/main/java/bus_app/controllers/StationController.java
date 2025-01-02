package bus_app.controllers;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Path;
import bus_app.model.Station;
import bus_app.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus_rep/stations")
public class StationController {

    @Autowired
    private StationRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Station addStation(@RequestBody Station item) throws IncorrectBodyException {

        return repository.createItem(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Station getStationById(@PathVariable("id") Integer id) throws NoDataException {

        return repository.findItemById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Station> getAllStations() {

        return repository.findAllItems();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Station updateStationById(@PathVariable("id") Integer id,
                               @RequestBody Station item) throws IncorrectBodyException {

        return repository.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStationById(@PathVariable("id") Integer id) throws NoDataException {

        repository.deleteItemById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllStations() {

        repository.deleteAllItems();
    }

    @GetMapping("/by_path/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getStationsInOrderByPathId(@PathVariable("id") Integer id) {

        return repository.getStationsInOrderByPathId(id);
    }
}
