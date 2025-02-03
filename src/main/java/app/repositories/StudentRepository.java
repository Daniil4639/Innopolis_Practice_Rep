package app.repositories;

import app.exceptions.NoDataException;
import app.mappers.StudentMapper;
import app.models.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class StudentRepository {

    private final JdbcTemplate template;

    public StudentRepository(JdbcTemplate template) {
        this.template = template;
    }

    public Student createStudent(Student student) {
        return template.queryForObject(
                String.format("insert into students values(default, '%s', '%s', ARRAY%s) returning *",
                        student.fullName(),
                        student.email(),
                        Arrays.toString(student.gradesList())),
                new StudentMapper()
        );
    }

    public Student readStudent(Integer id) throws NoDataException {
        try {
            return template.queryForObject(
                    "select * from students where id = ?",
                    new StudentMapper(),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("No record with id = " + id);
        }
    }

    public Student updateStudent(Integer id, Student student) {
        return template.queryForObject(
                String.format("update students set full_name = %s, email = %s, grades_list = %s where id = %d returning *",
                        (student.fullName() != null) ? ("'" + student.fullName() + "'") : ("full_name"),
                        (student.email() != null) ? ("'" + student.email() + "'") : ("email"),
                        (student.gradesList() != null) ? ("ARRAY" + Arrays.toString(student.gradesList())) : ("grades_list"),
                        id),
                new StudentMapper()
        );
    }

    public Student addGrade(Integer id, Integer gradeId) {
        return template.queryForObject(
                "update students set grades_list = array_append(grades_list, ?) where id = ? returning *",
                new StudentMapper(),
                gradeId,
                id
        );
    }

    public boolean deleteStudent(Integer id) {
        template.execute(String.format("delete from students where id = %d", id));

        return true;
    }
}
