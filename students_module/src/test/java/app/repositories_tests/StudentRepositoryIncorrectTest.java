package app.repositories_tests;

import app.repositories_tests.abstracts.TestDBContainerInitializer;
import app.exceptions.NoDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentRepositoryIncorrectTest extends TestDBContainerInitializer {

    public StudentRepositoryIncorrectTest() {
        super();
    }

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Students: incorrect read test")
    public void readTest() {
        try {
            repository.readStudent(9999);

            assert false;
        } catch (NoDataException ignored) {}
    }
}