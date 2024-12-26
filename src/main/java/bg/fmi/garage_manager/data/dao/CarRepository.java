package bg.fmi.garage_manager.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import bg.fmi.garage_manager.data.model.CarEntity;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    Optional<CarEntity> findByIdAndIsActiveTrue(Long id);

    @Query("SELECT c FROM CarEntity c WHERE "
         + "(:make IS NULL OR c.make LIKE CONCAT(:make, '%')) AND "
         + "(:garageId IS NULL OR :garageId MEMBER OF c.garages) AND "
         + "(:fromYear IS NULL OR c.productionYear >= :fromYear) AND "
         + "(:toYear IS NULL OR c.productionYear <= :toYear) AND "
         + "c.isActive = true")
    List<CarEntity> findAllWithOptionalParameters(
        @Param("make") String make,
        @Param("garageId") Long garageId,
        @Param("fromYear") Integer fromYear,
        @Param("toYear") Integer toYear
    );
}
