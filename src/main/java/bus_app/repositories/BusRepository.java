package bus_app.repositories;

import bus_app.models.Bus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {

    @Override
    @Query("select b from #{#entityName} b where b.isDeleted = false")
    List<Bus> findAll();

    @Override
    @Query("select b from #{#entityName} b where b.isDeleted = false and b.number = ?1")
    Optional<Bus> findById(String id);

    @Transactional
    @Override
    @Modifying
    @Query("update #{#entityName} b set b.isDeleted = true where b.number = ?1")
    void deleteById(String id);

    @Transactional
    @Modifying
    @Query("update #{#entityName} b set b.isDeleted = false where b.number = ?1")
    void returnToListById(String id);
}