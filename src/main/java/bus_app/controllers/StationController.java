package bus_app.controllers;

import bus_app.dto.stations.StationDto;
import bus_app.services.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public List<StationDto> getAllStations() {
        return stationService.readAllStations().stream()
                .map(StationDto::new).toList();
    }

    @PostMapping
    public StationDto addStation(@RequestBody StationDto station) {
        return new StationDto(stationService.addStation(station));
    }

    @PutMapping("/{id}")
    public StationDto updateStation(@PathVariable("id") Long id, @RequestBody StationDto station) {
        return new StationDto(stationService.updateStation(id, station));
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable("id") Long id) {
        stationService.deleteStation(id);
    }
}