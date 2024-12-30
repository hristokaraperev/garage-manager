package bg.fmi.garage_manager.processors;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

        List<MaintenanceEntity> carMaintenances = maintenanceRepository.findAllByCarIdAndScheduledDate(request.getCarId(), request.getScheduledDate());
        if (!carMaintenances.isEmpty()) {
            return null;
        }

        List<MaintenanceEntity> existingMaintenances = maintenanceRepository.findAllByGarageIdAndScheduledDate(request.getGarageId(), request.getScheduledDate());
        if (existingMaintenances.size() >= atGarage.getCapacity()) {
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

    public MaintenanceResponse getMaintenance(Long id) {
        MaintenanceEntity maintenance = maintenanceRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (maintenance == null) {
            return null;
        }

        return new MaintenanceResponse(
            maintenance.getId(), 
            maintenance.getCar().getId(), 
            maintenance.getCar().getLicensePlate(), 
            maintenance.getServiceType(), 
            maintenance.getScheduledDate(), 
            maintenance.getGarage().getId(), 
            maintenance.getGarage().getName()
            );
    }

    public MaintenanceResponse updateMaintenance(Long id, MaintenanceRequest request) {
        MaintenanceEntity existingMaintenance = maintenanceRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (existingMaintenance == null) {
            return null;
        }

        CarEntity toCar = carProcessor.getCarEntityById(request.getCarId());
        GarageEntity atGarage = garageProcessor.getGarageEntityById(request.getGarageId());

        if (toCar == null || atGarage == null) {
            return null;
        }

        List<MaintenanceEntity> existingMaintenances = maintenanceRepository.findAllByGarageIdAndScheduledDate(request.getGarageId(), request.getScheduledDate());
        if (existingMaintenances.size() >= atGarage.getCapacity()) {
            return null;
        }

        existingMaintenance.setCar(toCar);
        existingMaintenance.setGarage(atGarage);
        existingMaintenance.setServiceType(request.getServiceType());
        existingMaintenance.setScheduledDate(request.getScheduledDate());

        MaintenanceEntity updatedMaintenance = maintenanceRepository.save(existingMaintenance);

        return new MaintenanceResponse(
            updatedMaintenance.getId(), 
            toCar.getId(), 
            toCar.getLicensePlate(), 
            updatedMaintenance.getServiceType(), 
            updatedMaintenance.getScheduledDate(), 
            atGarage.getId(), 
            atGarage.getName()
        );
    }

    public MaintenanceResponse deleteMaintenance(Long id) {
        MaintenanceEntity existingMaintenance = maintenanceRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (existingMaintenance == null) {
            return null;
        }

        existingMaintenance.setIsActive(false);
        
        MaintenanceEntity deletedMaintenance = maintenanceRepository.save(existingMaintenance);

        return new MaintenanceResponse(
            deletedMaintenance.getId(), 
            deletedMaintenance.getCar().getId(), 
            deletedMaintenance.getCar().getLicensePlate(), 
            deletedMaintenance.getServiceType(), 
            deletedMaintenance.getScheduledDate(), 
            deletedMaintenance.getGarage().getId(), 
            deletedMaintenance.getGarage().getName()
        );
    }

    public List<MaintenanceResponse> searchMaintenance(Long carId, Long garageId, LocalDate startDate, LocalDate endDate) {
        List<MaintenanceEntity> maintenanceList = maintenanceRepository.findByCarIdAndGarageIdAndScheduledDateBetween(carId, garageId, startDate, endDate);
        return maintenanceList.stream()
            .map(maintenance -> new MaintenanceResponse(
            maintenance.getId(),
            maintenance.getCar().getId(),
            maintenance.getCar().getLicensePlate(),
            maintenance.getServiceType(),
            maintenance.getScheduledDate(),
            maintenance.getGarage().getId(),
            maintenance.getGarage().getName()
            ))
            .collect(Collectors.toList());
    }

    protected List<MaintenanceEntity> getMaintenanceEntitiesByGarageIdAndScheduledDateBetween(Long garageId, LocalDate startDate, LocalDate endDate) {
        return maintenanceRepository.findAllByGarageIdAndScheduledDateBetween(garageId, startDate, endDate);
    }
}
