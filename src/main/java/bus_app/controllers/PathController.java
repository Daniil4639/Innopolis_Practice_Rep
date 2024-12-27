package bus_app.controllers;

import bus_app.model.Driver;
import bus_app.model.Path;
import bus_app.repositories.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus_rep/paths")
public class PathController {

    @Autowired
    private PathRepository repository;

    @GetMapping("/get_driver")
    @ResponseStatus(HttpStatus.OK)
    public Driver getDriverByPath(@RequestParam("begin") String beginStation,
                                  @RequestParam("end") String endStation) {

        Driver res = repository.getDriverByPath(beginStation, endStation);
        System.out.println(res);

        return res;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public Path insertNewPath(@RequestParam("begin") String begin,
                              @RequestParam("end") String end,
                              @RequestParam("duration") Integer duration) {

        Path res = repository.insertNewPath(begin, end, duration);
        System.out.println(res);

        return res;
    }
}
