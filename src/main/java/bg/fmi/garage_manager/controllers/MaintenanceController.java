package bg.fmi.garage_manager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bg.fmi.garage_manager.data.requests.MaintenanceRequest;
import bg.fmi.garage_manager.data.responses.MaintenanceResponse;
import bg.fmi.garage_manager.processors.MaintenanceProcessor;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "http://localhost:3000")
public class MaintenanceController {
    
    private final MaintenanceProcessor maintenanceProcessor;

    public MaintenanceController(MaintenanceProcessor maintenanceProcessor) {
        this.maintenanceProcessor = maintenanceProcessor;
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
}
