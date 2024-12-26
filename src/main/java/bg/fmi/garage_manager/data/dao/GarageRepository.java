package bg.fmi.garage_manager.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.fmi.garage_manager.data.model.GarageEntity;

@Repository
public interface GarageRepository extends JpaRepository<GarageEntity, Long> {

    List<GarageEntity> findByIsActiveTrue();
    List<GarageEntity> findByCityAndIsActiveTrue(String city);
    Optional<GarageEntity> findByIdAndIsActiveTrue(Long id);
}
