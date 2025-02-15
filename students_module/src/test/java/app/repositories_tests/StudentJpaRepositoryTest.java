package app.repositories_tests;

import app.models.Student;
import app.repositories.StudentJpaRepository;
import app.repositories_tests.abstracts.TestDBContainerInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StudentJpaRepositoryTest extends TestDBContainerInitializer {

    @BeforeAll
    public static void migrate() {
        flyway.clean();
        flyway.migrate();
    }

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Test
    @DisplayName("Students: jpa age more test")
    public void findStudentsWithMoreThanAgeTest() {
        List<Integer> ages = studentJpaRepository.findStudentsWithMoreThanAge(30).stream()
                .map(Student::getAge)
                .toList();

        assert ages.size() == 2;
        assert ages.contains(37) && ages.contains(42);
    }

    @Test
    @DisplayName("Students: jpa age less test")
    public void findStudentsWithLessThanAgeTest() {
        List<Integer> ages = studentJpaRepository.findStudentsWithLessThanAge(30).stream()
                .map(Student::getAge)
                .toList();

        assert ages.size() == 2;
        assert ages.contains(28) && ages.contains(19);
    }

    @Test
    @DisplayName("Students: jpa age equal test")
    public void findStudentsWithEqualThanAgeTest() {
        List<Integer> ages = studentJpaRepository.findStudentsWithAge(28).stream()
                .map(Student::getAge)
                .toList();

        assert ages.size() == 1;
        assert ages.contains(28);
    }

    @Test
    @DisplayName("Students: jpa name test")
    public void findByOrderByFullNameTest() {
        List<String> names = studentJpaRepository.findByOrderByFullName()
                .stream().map(Student::getFullName).toList();

        List<String> list = List.of("Козлов Александр Савельевич",
                "Львов Константин Павлович",
                "Наумова Анна Артёмовна",
                "Овчинникова Вероника Максимовна");

        assert names.equals(list);
    }

    @Test
    @DisplayName("Students: jpa email test")
    public void findTop1ByEmailTest() {
        List<String> emails = studentJpaRepository.findTop1ByEmail()
                .stream().map(Student::getEmail).toList();

        assert emails.get(0).equals("vewad_ewicu45@hotmail.com");
    }
}
