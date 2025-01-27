package app.configurations;

import app.packages.QueriesH2Package;
import app.packages.QueriesPostgresPackage;
import app.packages.interfaces.QueriesPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class QueriesPackageConfig {

    @Bean
    public QueriesPackage getPostgresPackage() {
        return new QueriesPostgresPackage();
    }

    @Bean
    @Profile("debug")
    @Primary
    public QueriesPackage getH2Package() {
        return new QueriesH2Package();
    }
}
