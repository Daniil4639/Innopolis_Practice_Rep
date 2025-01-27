package app.mappers;

import app.models.Note;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRowMapper implements RowMapper<Note> {

    @Override
    public Note mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Note(
                rs.getInt("id"),
                rs.getTimestamp("date_and_time").toLocalDateTime(),
                rs.getString("topic"),
                rs.getString("text")
        );
    }
}