package app.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Getter
public class NotesAppConfig {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(new DriverManagerDataSource(
                dbUrl,
                dbUsername,
                dbPassword
        ));
    }
}