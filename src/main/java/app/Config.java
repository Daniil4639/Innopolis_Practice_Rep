package app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootConfiguration
public class Config {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.user}")
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
