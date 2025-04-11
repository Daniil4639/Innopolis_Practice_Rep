package bus_app.controllers;

import bus_app.dto.buses.BusRequestDto;
import bus_app.dto.buses.BusResponseDto;
import bus_app.services.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @GetMapping
    public List<BusResponseDto> getAllBuses() {
        return busService.readAllBuses().stream()
                .map(BusResponseDto::new)
                .toList();
    }

    @PostMapping
    public BusResponseDto addBus(@RequestBody BusRequestDto bus) {
        return new BusResponseDto(busService.createBus(bus));
    }

    @PutMapping
    public BusResponseDto updateBus(@RequestBody BusRequestDto bus) {
        return new BusResponseDto(busService.updateBus(bus));
    }

    @DeleteMapping
    public void deleteBus(@RequestBody String number) {
        busService.deleteBus(number);
    }
}