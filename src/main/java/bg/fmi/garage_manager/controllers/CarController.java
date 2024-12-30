package bg.fmi.garage_manager.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import bg.fmi.garage_manager.data.requests.CarRequest;
import bg.fmi.garage_manager.data.responses.CarResponse;
import bg.fmi.garage_manager.processors.CarProcessor;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    private final CarProcessor carProcessor;

    public CarController(CarProcessor carProcessor) {
        this.carProcessor = carProcessor;
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCarsAtGarageMadeFromYearToYear(
        @RequestParam(required = false) String carMake,
        @RequestParam(required = false) Long garageId,
        @RequestParam(required = false) Integer fromYear,
        @RequestParam(required = false) Integer toYear) {
        try {
            List<CarResponse> cars = carProcessor.getAllCarsAtGarageMadeFromYearToYear(carMake, garageId, fromYear, toYear);
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@Valid @RequestBody CarRequest request) {
        try {
            CarResponse savedItem = carProcessor.createCar(request);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Long id) {
        try {
            CarResponse car = carProcessor.getCarById(id);
            if (car == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable Long id, @Valid @RequestBody CarRequest request) {
        try {
            CarResponse updatedItem = carProcessor.updateCar(id, request);
            if (updatedItem == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarResponse> deleteCar(@PathVariable Long id) {
        try {
            CarResponse deletedItem = carProcessor.deleteCar(id);
            if (deletedItem == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
