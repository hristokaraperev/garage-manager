package bg.fmi.garage_manager.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bg.fmi.garage_manager.data.requests.GarageRequest;
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
        public ResponseEntity<GarageResponse> createGarage(@Valid @RequestBody GarageRequest item) {
            try {
                GarageResponse savedItem = garageProcessor.createGarage(item);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping
        public ResponseEntity<List<GarageResponse>> getGarages(@RequestParam(required = false) String city) {
            try {
                List<GarageResponse> garages;
                if (city != null) {
                    garages = garageProcessor.getGaragesByCity(city);
                } else {
                    garages = garageProcessor.getAllGarages();
                }
                return new ResponseEntity<>(garages, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<GarageResponse> getGarageById(@PathVariable Long id) {
            try {
                GarageResponse item = garageProcessor.getGarageById(id);
                if (item == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(item, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<GarageResponse> updateGarage(@PathVariable Long id, @Valid @RequestBody GarageRequest item) {
            try {
            GarageResponse updatedItem = garageProcessor.updateGarage(id, item);
            if (updatedItem == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
            } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> deleteGarage(@PathVariable Long id) {
            try {
                GarageResponse deletedItem = garageProcessor.deleteGarage(id);
                if (deletedItem == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
}
