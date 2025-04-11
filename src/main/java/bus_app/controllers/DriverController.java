package bus_app.controllers;

import bus_app.dto.drivers.DriverDto;
import bus_app.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @GetMapping
    public List<DriverDto> getAllDrivers() {
        return driverService.readAllDrivers().stream()
                .map(DriverDto::new).toList();
    }

    @PostMapping
    public DriverDto addDriver(@RequestBody DriverDto driver) {
        return new DriverDto(driverService.addDriver(driver));
    }

    @PutMapping("/{id}")
    public DriverDto updateDriver(@PathVariable("id") Long id, @RequestBody DriverDto driverDto) {
        return new DriverDto(driverService.updateDriver(id, driverDto));
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable("id") Long id) {
        driverService.deleteDriver(id);
    }
}