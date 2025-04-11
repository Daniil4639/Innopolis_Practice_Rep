package bus_app.controllers;

import bus_app.dto.paths.PathRequestDto;
import bus_app.dto.paths.PathResponseDto;
import bus_app.services.PathService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paths")
@RequiredArgsConstructor
public class PathController {

    private final PathService pathService;

    @GetMapping
    public List<PathResponseDto> getAllPaths() {
        return pathService.readAllPaths().stream()
                .map(PathResponseDto::new).toList();
    }

    @PostMapping
    public PathResponseDto addPath(@RequestBody PathRequestDto path) {
        return new PathResponseDto(pathService.addPath(path));
    }

    @PutMapping
    public PathResponseDto updatePath(@RequestBody PathRequestDto path) {
        return new PathResponseDto(pathService.updatepath(path));
    }

    @DeleteMapping
    public void deletePath(@RequestBody String number) {
        pathService.deletePath(number);
    }
}