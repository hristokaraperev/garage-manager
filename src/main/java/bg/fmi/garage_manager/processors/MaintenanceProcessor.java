package bg.fmi.garage_manager.processors;

import org.springframework.stereotype.Service;

import bg.fmi.garage_manager.data.dao.MaintenanceRepository;
import bg.fmi.garage_manager.data.model.CarEntity;
import bg.fmi.garage_manager.data.model.GarageEntity;
import bg.fmi.garage_manager.data.model.MaintenanceEntity;
import bg.fmi.garage_manager.data.requests.MaintenanceRequest;
import bg.fmi.garage_manager.data.responses.MaintenanceResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MaintenanceProcessor {
    
    private final MaintenanceRepository maintenanceRepository;
    private final CarProcessor carProcessor;
    private final GarageProcessor garageProcessor;

    public MaintenanceProcessor(MaintenanceRepository maintenanceRepository, CarProcessor carProcessor, GarageProcessor garageProcessor) {
        this.maintenanceRepository = maintenanceRepository;
        this.carProcessor = carProcessor;
        this.garageProcessor = garageProcessor;
    }

    public MaintenanceResponse createMaintenance(MaintenanceRequest request) {
        MaintenanceEntity newMaintenance = new MaintenanceEntity();
        
        CarEntity toCar = carProcessor.getCarEntityById(request.getCarId());
        GarageEntity atGarage = garageProcessor.getGarageEntityById(request.getGarageId());
        
        if (toCar == null || atGarage == null) {
            return null;
        }

        newMaintenance.setCar(toCar);
        newMaintenance.setGarage(atGarage);
        newMaintenance.setServiceType(request.getServiceType());
        newMaintenance.setScheduledDate(request.getScheduledDate());
        newMaintenance.setIsActive(true);

        MaintenanceEntity savedMaintenance = maintenanceRepository.save(newMaintenance);

        return new MaintenanceResponse(
            savedMaintenance.getId(), 
            toCar.getId(), 
            toCar.getLicensePlate(), 
            savedMaintenance.getServiceType(), 
            savedMaintenance.getScheduledDate(), 
            atGarage.getId(), 
            atGarage.getName()
            );
    }

}
