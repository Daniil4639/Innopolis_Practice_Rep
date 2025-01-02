package bus_app.controllers;

import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.model.Driver;
import bus_app.model.Path;
import bus_app.repositories.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bus_rep/paths")
public class PathController {

    @Autowired
    private PathRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Path addPath(@RequestBody Path item) throws IncorrectBodyException {

        return repository.createItem(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Path getPathById(@PathVariable("id") Integer id) throws NoDataException {

        return repository.findItemById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Path> getAllPaths() {

        return repository.findAllItems();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Path updatePathById(@PathVariable("id") Integer id,
                                   @RequestBody Path item) throws IncorrectBodyException {

        return repository.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePathById(@PathVariable("id") Integer id) throws NoDataException {

        repository.deleteItemById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllPaths() {

        repository.deleteAllItems();
    }

    @GetMapping("/get_driver")
    @ResponseStatus(HttpStatus.OK)
    public Driver getDriverByPath(@RequestParam("begin") String beginStation,
                                  @RequestParam("end") String endStation) {

        Driver res = repository.getDriverByPath(beginStation, endStation);
        System.out.println(res);

        return res;
    }

    @GetMapping("/get_stations")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, List<Integer>> getStationGroupsByPath() {

        return repository.getStationGroupsByPath();
    }
}
