package app;

import app.configurations.NotesAppConfig;
import app.mappers.NoteRowMapper;
import app.models.Note;
import app.repositories.NoteRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {NotesAppConfig.class, NoteRepository.class})
public class NotesCorrectTest extends TestDBContainerInitializer {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private JdbcTemplate template;

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: correct create test")
    public void testCreate() {
        LocalDateTime currentTime = LocalDateTime.now();

        Note note = new Note(null, currentTime, "someTopic", "someText");
        Integer id = repository.createNote(note).getId();

        Note receivedNote = template.queryForObject(
                "select * from notes where id = ?",
                new NoteRowMapper(), id
        );

        assertThat(note.getDateAndTime().minusNanos(note.getDateAndTime().getNano()))
                .isEqualTo(receivedNote.getDateAndTime().minusNanos(receivedNote.getDateAndTime().getNano()));
        assertThat(note.getTopic()).isEqualTo(receivedNote.getTopic());
        assertThat(note.getText()).isEqualTo(receivedNote.getText());

        template.execute(String.format("delete from notes where id = %d", id));
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: correct read test")
    public void testRead() {
        Note note = new Note(null, LocalDateTime.now(), "someTopic", "someText");
        Integer id = template.queryForObject(
                String.format("insert into notes values(default, '%s', '%s', '%s') returning id",
                        note.getDateAndTime().toString(),
                        note.getTopic(),
                        note.getText()),
                Integer.class
        );

        Note receivedNote = repository.readNote(id);

        assertThat(note.getText()).isEqualTo(receivedNote.getText());
        assertThat(note.getTopic()).isEqualTo(receivedNote.getTopic());

        template.execute(String.format("delete from notes where id = %d", id));
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: correct update test")
    public void testUpdate() {
        Note note = new Note(null, LocalDateTime.now(), "someTopic", "someText");
        Integer id = template.queryForObject(
                String.format("insert into notes values(default, '%s', '%s', '%s') returning id",
                        note.getDateAndTime().toString(),
                        note.getTopic(),
                        note.getText()),
                Integer.class
        );

        Note receivedNote = repository.updateNote(id,
                new Note(null, null, "someNewTopic", null));

        assertThat(receivedNote.getTopic()).isEqualTo("someNewTopic");
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: correct delete test")
    public void testDelete() {
        Note note = new Note(null, LocalDateTime.now(), "someTopic", "someText");
        Integer id = template.queryForObject(
                String.format("insert into notes values(default, '%s', '%s', '%s') returning id",
                        note.getDateAndTime().toString(),
                        note.getTopic(),
                        note.getText()),
                Integer.class
        );

        repository.deleteNote(id);

        boolean isOk = false;
        try {
            template.queryForObject(
                    "select * from notes where id = ?",
                    new NoteRowMapper(),
                    id
            );
        } catch (EmptyResultDataAccessException ex) {
            isOk = true;
        }

        assertThat(isOk).isTrue();
    }
}
