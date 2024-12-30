package bg.fmi.garage_manager.processors;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import bg.fmi.garage_manager.data.responses.GarageDailyAvailabilityResponse;
import bg.fmi.garage_manager.data.responses.MaintenanceMonthlyRequestsResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReportProcessor {
    
    private final GarageProcessor garageProcessor;
    private final CarProcessor carProcessor;
    private final MaintenanceProcessor maintenanceProcessor;

    public ReportProcessor(GarageProcessor garageProcessor, CarProcessor carProcessor, MaintenanceProcessor maintenanceProcessor) {
        this.garageProcessor = garageProcessor;
        this.carProcessor = carProcessor;
        this.maintenanceProcessor = maintenanceProcessor;
    }

    public List<GarageDailyAvailabilityResponse> getDailyAvailabilityReport(Long garageId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public List<MaintenanceMonthlyRequestsResponse> getMonthlyMaintenanceReport(Long garageId, String startDate, String endDate) {
        return null;
    }
}
