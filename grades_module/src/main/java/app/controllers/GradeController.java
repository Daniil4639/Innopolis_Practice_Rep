package app.controllers;

import app.dto.GradeDto;
import app.exceptions.NoDataException;
import app.services.GradeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService service;

    public GradeController(GradeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public GradeDto readGrade(@PathVariable("id") Integer id) throws NoDataException {
        return new GradeDto(service.readGrade(id));
    }

    @PutMapping("/{id}")
    public GradeDto makeGradeActive(@PathVariable("id") Integer id) throws NoDataException {
        return new GradeDto(service.makeGradeActive(id));
    }

    @DeleteMapping("/{id}")
    public GradeDto makeGradeNonActive(@PathVariable("id") Integer id) throws NoDataException {
        return new GradeDto(service.makeGradeNonActive(id));
    }
}
