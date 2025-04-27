package bus_app.caches;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class BusAppCacheConfig {

    @Bean
    public CacheManager getCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager(
                "buses", "departments", "drivers", "paths", "stations", "users");

        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(500));

        return manager;
    }
}