package bg.fmi.garage_manager.processors;

import org.springframework.stereotype.Service;

import bg.fmi.garage_manager.data.dao.GarageRepository;
import bg.fmi.garage_manager.data.model.GarageEntity;
import bg.fmi.garage_manager.data.requests.CreateGarageRequest;
import bg.fmi.garage_manager.data.responses.GarageResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GarageProcessor {

    private final GarageRepository garageRepository;

    public GarageProcessor(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
        }

    public GarageResponse createGarage(CreateGarageRequest request) {
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
}
