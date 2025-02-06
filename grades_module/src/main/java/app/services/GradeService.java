package app.services;

import app.exceptions.NoDataException;
import app.models.Grade;
import app.repositories.GradeRepository;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    private final GradeRepository repository;

    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }

    public Grade makeGradeActive(Integer id) throws NoDataException {
        repository.readGrade(id);

        return repository.makeGradeActive(id);
    }

    public Grade makeGradeNonActive(Integer id) throws NoDataException {
        repository.readGrade(id);

        return repository.makeGradeNonActive(id);
    }

    public Grade readGrade(Integer id) throws NoDataException {
        return repository.readGrade(id);
    }
}
