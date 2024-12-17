package bus_app.controllers;

import bus_app.model.Station;
import bus_app.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus_rep/stations")
public class StationController {

    @Autowired
    private StationRepository repository;

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Station getStationByName(@PathVariable("name") String name) {
        Station res = repository.getStationByName(name);
        System.out.println(res);

        return res;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Station insertNewStation(@RequestParam("name") String name,
                                    @RequestParam("district") String district,
                                    @RequestParam("paths[]") Integer[] pathIds,
                                    @RequestParam("durations[]") Integer[] durations) {

        Station res = repository.insertNewStation(name, district, pathIds, durations);
        System.out.println(res);

        return res;
    }
}
