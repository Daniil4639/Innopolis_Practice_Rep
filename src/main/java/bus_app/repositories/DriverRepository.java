package bus_app.repositories;

import bus_app.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Override
    @Query("select b from #{#entityName} b where b.isDeleted = false")
    List<Driver> findAll();

    @Override
    @Query("select b from #{#entityName} b where b.isDeleted = false and b.id = ?1")
    Optional<Driver> findById(Long id);

    @Override
    @Modifying
    @Query("update #{#entityName} b set b.isDeleted = true where b.id = ?1")
    void deleteById(Long id);
}