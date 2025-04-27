package bus_app.repositories;

import bus_app.models.BusUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "users")
public interface BusUserRepository extends JpaRepository<BusUser, String> {

    @Cacheable(key = "#id")
    Optional<BusUser> findById(String id);
}