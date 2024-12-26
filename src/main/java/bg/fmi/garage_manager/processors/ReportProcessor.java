package bg.fmi.garage_manager.processors;

import java.time.LocalDate;
import java.util.List;

import bg.fmi.garage_manager.data.responses.GarageDailyAvailabilityResponse;

public class ReportProcessor {
    
    private final GarageProcessor garageProcessor;

    public ReportProcessor(GarageProcessor garageProcessor) {
        this.garageProcessor = garageProcessor;
    }

    public List<GarageDailyAvailabilityResponse> getDailyAvailabilityReport(Long garageId, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
