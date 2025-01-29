package app.repositories_tests;

import org.flywaydb.core.Flyway;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public class TestDBContainerInitializer {

    public static PostgreSQLContainer<?> POSTGRES;

    public static Flyway flyway;

    public static DataSource source;

    static {
        System.setProperty("DB_URL", "jdbc:postgresql://localhost:5432/bus_db");
        System.setProperty("DB_USER", "postgres");
        System.setProperty("DB_PASSWORD", "postgres");

        POSTGRES = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("bus_db")
                .withUsername("postgres")
                .withPassword("postgres");
        POSTGRES.start();

        source = new DriverManagerDataSource(
                POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),
                POSTGRES.getPassword()
        );

        flyway = Flyway.configure()
                .cleanDisabled(false)
                .dataSource(source)
                .load();
    }
}
