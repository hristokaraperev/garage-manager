package bg.fmi.garage_manager.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bg.fmi.garage_manager.data.model.GarageEntity;

@Repository
public interface GarageRepository extends JpaRepository<GarageEntity, Long> {

}
