package bg.fmi.garage_manager.processors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.fmi.garage_manager.data.dao.GarageRepository;
import bg.fmi.garage_manager.data.model.GarageEntity;
import bg.fmi.garage_manager.data.requests.GarageRequest;
import bg.fmi.garage_manager.data.responses.GarageResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GarageProcessor {

    private final GarageRepository garageRepository;

    public GarageProcessor(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
        }

    public GarageResponse createGarage(GarageRequest request) {
        GarageEntity newGarage = new GarageEntity();
        newGarage.setName(request.getName());
        newGarage.setLocation(request.getLocation());
        newGarage.setCity(request.getCity());
        newGarage.setCapacity(request.getCapacity());
        newGarage.setIsActive(true);

        GarageEntity savedGarage = garageRepository.save(newGarage);

        return new GarageResponse(
            savedGarage.getId(), 
            savedGarage.getName(), 
            savedGarage.getLocation(), 
            savedGarage.getCity(), 
            savedGarage.getCapacity()
            );
    }

    public List<GarageResponse> getAllGarages() {
        List<GarageEntity> activeGarages = garageRepository.findByIsActiveTrue();
        return activeGarages.stream()
            .map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            ))
            .collect(Collectors.toList());
    }


    public List<GarageResponse> getGaragesByCity(String city) {
        List<GarageEntity> activeGarages = garageRepository.findByCityAndIsActiveTrue(city);
        return activeGarages.stream()
            .map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            ))
            .collect(Collectors.toList());
    }

    protected GarageEntity getGarageEntityById(Long id) {
        return garageRepository.findByIdAndIsActiveTrue(id).orElse(null);
    }

    public GarageResponse getGarageById(Long id) {
        GarageEntity garage = garageRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (garage == null) {
            return null;
        }
        return new GarageResponse(
            garage.getId(),
            garage.getName(),
            garage.getLocation(),
            garage.getCity(),
            garage.getCapacity()
        );
    }

    public GarageResponse updateGarage(Long id, GarageRequest request) {
        GarageEntity garage = garageRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (garage == null) {
            return null;
        }

        garage.setName(request.getName());
        garage.setLocation(request.getLocation());
        garage.setCity(request.getCity());
        garage.setCapacity(request.getCapacity());

        GarageEntity updatedGarage = garageRepository.save(garage);

        return new GarageResponse(
            updatedGarage.getId(),
            updatedGarage.getName(),
            updatedGarage.getLocation(),
            updatedGarage.getCity(),
            updatedGarage.getCapacity()
        );
    }

    public GarageResponse deleteGarage(Long id) {
        GarageEntity garage = garageRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (garage == null) {
            return null;
        }

        garage.setIsActive(false);
        
        GarageEntity deletedGarage = garageRepository.save(garage);

        return new GarageResponse(
            deletedGarage.getId(),
            deletedGarage.getName(),
            deletedGarage.getLocation(),
            deletedGarage.getCity(),
            deletedGarage.getCapacity()
        );
    }

    protected int getGarageCapacity(Long garageId) {
        GarageEntity garage = garageRepository.findByIdAndIsActiveTrue(garageId).orElse(null);
        if (garage == null) {
            return 0;
        }
        return garage.getCapacity();
    }
}
