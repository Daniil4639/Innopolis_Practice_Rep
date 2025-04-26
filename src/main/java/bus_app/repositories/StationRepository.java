package bus_app.repositories;

import bus_app.models.Station;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    @Override
    @Query("select b from #{#entityName} b where b.isDeleted = false")
    List<Station> findAll();

    @Override
    @Query("select b from #{#entityName} b where b.isDeleted = false and b.id = ?1")
    Optional<Station> findById(Long id);

    @Transactional
    @Override
    @Modifying
    @Query("update #{#entityName} b set b.isDeleted = true where b.id = ?1")
    void deleteById(Long id);
}