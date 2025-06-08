package app.repositories;

import app.exceptions.NoDataException;
import app.mappers.GradeMapper;
import app.models.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames = "grades")
public class GradeRepository {

    private final JdbcTemplate template;

    @Cacheable(key = "'all'")
    public List<Grade> readAllGrades() {
        return template.query(
                "select * from grades",
                new GradeMapper()
        );
    }

    @Caching(
            put = @CachePut(key = "#id"),
            evict = @CacheEvict(key = "'all'")
    )
    public Grade makeGradeActive(Integer id) {
        return template.queryForObject(
                "update grades set is_active = true where id = ? returning *",
                new GradeMapper(),
                id
        );
    }

    @Caching(
            put = @CachePut(key = "#id"),
            evict = @CacheEvict(key = "'all'")
    )
    public Grade makeGradeNonActive(Integer id) {
        return template.queryForObject(
                "update grades set is_active = false where id = ? returning *",
                new GradeMapper(),
                id
        );
    }

    @Cacheable(key = "#id")
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

    @Caching(
            evict = {@CacheEvict(key = "#id"), @CacheEvict(key = "'all'")}
    )
    public void putGradeIntoArchive(Integer id) {
        template.execute(
                "update grades set is_archived = true where id = " + id
        );
    }
}