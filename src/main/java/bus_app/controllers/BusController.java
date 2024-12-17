package bus_app.controllers;

import bus_app.model.*;
import bus_app.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bus_rep/buses")
public class BusController {

    @Autowired
    private BusRepository repository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bus getBusByNum(@PathVariable("id") Integer id) {
        Bus res = repository.getBusByNum(id);
        System.out.println(res);

        return res;
    }

    @GetMapping("/{id}/get_stations")
    @ResponseStatus(HttpStatus.OK)
    public List<Station> getStationsInOrderByBusNumber(@PathVariable("id") Integer id) {
        List<Station> res = repository.getStationsByBusNumber(id);
        System.out.println(res);

        return res;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bus updateBusInfo(@PathVariable("id") Integer id,
                             @RequestParam("number") Optional<String> number,
                             @RequestParam("driver_id") Optional<Integer> driverId,
                             @RequestParam("path_id") Optional<Integer> pathId,
                             @RequestParam("department_id") Optional<Integer> departmentId,
                             @RequestParam("seats_number") Optional<Integer> seatsNumber,
                             @RequestParam("type") Optional<String> type) {

        Bus res = repository.updateBusById(new Bus(
                id, number.orElse(null), driverId.orElse(null), pathId.orElse(null),
                departmentId.orElse(null), seatsNumber.orElse(null), type.orElse(null)));
        System.out.println(res);

        return res;
    }
}