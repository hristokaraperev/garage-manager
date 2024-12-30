package bg.fmi.garage_manager.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bg.fmi.garage_manager.data.requests.MaintenanceRequest;
import bg.fmi.garage_manager.data.responses.MaintenanceMonthlyRequestsResponse;
import bg.fmi.garage_manager.data.responses.MaintenanceResponse;
import bg.fmi.garage_manager.processors.MaintenanceProcessor;
import bg.fmi.garage_manager.processors.ReportProcessor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "http://localhost:3000")
public class MaintenanceController {
    
    private final MaintenanceProcessor maintenanceProcessor;
    private final ReportProcessor reportProcessor;

    public MaintenanceController(MaintenanceProcessor maintenanceProcessor, ReportProcessor reportProcessor) {
        this.maintenanceProcessor = maintenanceProcessor;
        this.reportProcessor = reportProcessor;
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponse> createMaintenance(@Valid @RequestBody MaintenanceRequest request) {
        try {
            MaintenanceResponse savedMaintenance = maintenanceProcessor.createMaintenance(request);
            if (savedMaintenance == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(savedMaintenance, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> getMaintenance(@PathVariable Long id) {
        try {
            MaintenanceResponse maintenance = maintenanceProcessor.getMaintenance(id);
            if (maintenance == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(maintenance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> updateMaintenance(@PathVariable Long id, @RequestBody MaintenanceRequest entity) {
        try {
            MaintenanceResponse updatedMaintenance = maintenanceProcessor.updateMaintenance(id, entity);
            if (updatedMaintenance == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedMaintenance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> deleteMaintenance(@PathVariable Long id) {
        try {
            MaintenanceResponse deletedMaintenance = maintenanceProcessor.deleteMaintenance(id);
            if (deletedMaintenance == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponse>> searchMaintenance(
        @RequestParam(required = false) Long carId,
        @RequestParam(required = false) Long garageId,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {
        try {
        List<MaintenanceResponse> maintenanceRecords = maintenanceProcessor.searchMaintenance(carId, garageId, startDate, endDate);
        return new ResponseEntity<>(maintenanceRecords, HttpStatus.OK);
        } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/monthlyRequestsReport")
    public ResponseEntity<List<MaintenanceMonthlyRequestsResponse>> getMonthlyRequestsReportsByGarage(
        @RequestParam(required = true) Long garageId, 
        @RequestParam(required = true) String startMonth, 
        @RequestParam(required = true) String endMonth) {   
        try {
            List<MaintenanceMonthlyRequestsResponse> maintenanceMonthlyRequests = reportProcessor.getMonthlyMaintenanceReport(garageId, startMonth, endMonth);
            return new ResponseEntity<>(maintenanceMonthlyRequests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}
