package bg.fmi.garage_manager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bg.fmi.garage_manager.data.requests.CreateGarageRequest;
import bg.fmi.garage_manager.data.responses.GarageResponse;
import bg.fmi.garage_manager.processors.GarageProcessor;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/garages")
@CrossOrigin(origins = "http://localhost:3000")
public class GarageController {
    
    private final GarageProcessor garageProcessor;

    public GarageController(GarageProcessor garageProcessor) {
        this.garageProcessor = garageProcessor;
    }

        @PostMapping
        public ResponseEntity<GarageResponse> create(@Valid @RequestBody CreateGarageRequest item) {
            try {
                GarageResponse savedItem = garageProcessor.createGarage(item);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }


}
