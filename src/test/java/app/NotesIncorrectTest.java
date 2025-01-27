package app;

import app.configurations.NotesAppConfig;
import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;
import app.models.Note;
import app.repositories.NoteRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = {NotesAppConfig.class, NoteRepository.class})
public class NotesIncorrectTest extends TestDBContainerInitializer {

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
    @DisplayName("Notes: incorrect create test")
    public void testCreate() {
        Note note = new Note(null, null, "someTopic", "someText");

        try {
            repository.createNote(note);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: incorrect read test")
    public void testRead() {
        try {
            repository.readNote(1);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: incorrect update test")
    public void testUpdate() {
        Note note = new Note(null, null, "someTopic", "someText");

        try {
            repository.updateNote(1, note);
            assertThat(true).isFalse();
        } catch (IncorrectBodyException ignored) {}
    }

    @Test
    @SneakyThrows
    @DisplayName("Notes: incorrect delete test")
    public void testDelete() {
        try {
            repository.deleteNote(1);
            assertThat(true).isFalse();
        } catch (NoDataException ignored) {}
    }
}
