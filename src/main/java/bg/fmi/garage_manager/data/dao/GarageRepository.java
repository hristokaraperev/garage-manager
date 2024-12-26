package bg.fmi.garage_manager.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bg.fmi.garage_manager.data.model.GarageEntity;

@Repository
public interface GarageRepository extends JpaRepository<GarageEntity, Long> {

    List<GarageEntity> findByIsActiveTrue();
    @Query("SELECT g FROM GarageEntity g WHERE "
        + "(:city IS NULL OR g.city LIKE CONCAT(:city, '%')) AND "
        + "g.isActive = true")
    List<GarageEntity> findByCityAndIsActiveTrue(@Param("city")String city);
    Optional<GarageEntity> findByIdAndIsActiveTrue(Long id);
}
