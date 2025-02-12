package app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class StudentJdbcConfig {

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setSchema("students_schema");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/mvc_test_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return new JdbcTemplate(dataSource);
    }
}
