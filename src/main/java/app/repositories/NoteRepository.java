package app.repositories;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.mappers.NoteRowMapper;
import app.models.Note;
import app.packages.interfaces.QueriesPackage;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NoteRepository {

    private final JdbcTemplate template;

    private final QueriesPackage queriesPackage;

    public Note createNote(Note note) throws IncorrectBodyException {
        Note.isNoteCorrect(note);

        return template.queryForObject(
                String.format(queriesPackage.getCreateQuery(),
                        note.getDateAndTime().toString(),
                        note.getTopic(),
                        note.getText()),
                new NoteRowMapper()
        );
    }

    public Note readNote(Integer id) throws NoDataException {
        try {
            return template.queryForObject(
                    queriesPackage.getReadQuery(),
                    new NoteRowMapper(),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new NoDataException("Запись с идентификатором '" + id + "' отсутствует!");
        }
    }

    public Note updateNote(Integer id, Note note) throws IncorrectBodyException {
        try {
            readNote(id);
        } catch (NoDataException ex) {
            return createNote(note);
        }

        return template.queryForObject(
                String.format(queriesPackage.getUpdateQuery(),
                        (note.getDateAndTime() != null) ? ("'" + note.getDateAndTime() + "'") : ("date_and_time"),
                        (note.getTopic() != null) ? ("'" + note.getTopic() + "'") : ("topic"),
                        (note.getText() != null) ? ("'" + note.getText() + "'") : ("text"),
                        id),
                new NoteRowMapper()
        );
    }

    public void deleteNote(Integer id) throws NoDataException {
        readNote(id);

        template.execute(String.format(queriesPackage.getDeleteQuery(), id));
    }
}
