package app.repositories;

import app.exceptions.NoDataException;
import app.mappers.GradeMapper;
import app.models.Grade;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GradeRepository {

    private final JdbcTemplate template;

    public GradeRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Grade readGrade(Integer id) throws NoDataException {
        try {
            return template.queryForObject(
                    "select * from grades where id = ?",
                    new GradeMapper(),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("No record with id = " + id);
        }
    }
}
