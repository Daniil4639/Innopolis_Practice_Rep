package app.configs;

import app.clients.GradeClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Getter
public class StudentConfig {

    @Value("${grade.client.url}")
    private String gradeClientUrl;

    @Value("${spring.datasource.driver}")
    private String dataDriver;

    @Value("${spring.datasource.url}")
    private String dataUrl;

    @Value("${spring.datasource.username}")
    private String dataUser;

    @Value("${spring.datasource.password}")
    private String dataPassword;

    @Value("${spring.datasource.schema}")
    private String dataSchema;

    @Bean
    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataDriver);
        dataSource.setSchema(dataSchema);
        dataSource.setUrl(dataUrl);
        dataSource.setUsername(dataUser);
        dataSource.setPassword(dataPassword);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public GradeClient getGradeClient() {
        return new GradeClient(gradeClientUrl);
    }
}
