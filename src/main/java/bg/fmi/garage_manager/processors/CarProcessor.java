package bg.fmi.garage_manager.processors;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bg.fmi.garage_manager.data.dao.CarRepository;
import bg.fmi.garage_manager.data.model.CarEntity;
import bg.fmi.garage_manager.data.model.GarageEntity;
import bg.fmi.garage_manager.data.requests.CarRequest;
import bg.fmi.garage_manager.data.responses.CarResponse;
import bg.fmi.garage_manager.data.responses.GarageResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CarProcessor {
    
    private final CarRepository carRepository;
    private final GarageProcessor garageProcessor;

    public CarProcessor(CarRepository carRepository, GarageProcessor garageProcessor) {
        this.carRepository = carRepository;
        this.garageProcessor = garageProcessor;
    }

    public List<CarResponse> getAllCarsAtGarageMadeFromYearToYear(String carMake, Long garageId, Integer fromYear, Integer toYear) {
        List<CarEntity> cars = carRepository.findAllWithOptionalParameters(carMake, garageId, fromYear, toYear);
        return cars.stream().map(car -> new CarResponse(
            car.getId(),
            car.getMake(),
            car.getModel(),
            car.getProductionYear(),
            car.getLicensePlate(),
            car.getGarages().stream().map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    public CarResponse createCar(CarRequest request) {
        CarEntity newCar = new CarEntity();

        newCar.setMake(request.getMake());
        newCar.setModel(request.getModel());
        if (request.getProductionYear() > Year.now().getValue()) {
            throw new IllegalArgumentException("Production year cannot be in the future.");
        }
        newCar.setProductionYear(request.getProductionYear());
        newCar.setLicensePlate(request.getLicensePlate());
        newCar.setIsActive(true);
        List<GarageEntity> garages = new ArrayList<>();
        for (Long garageId : request.getGarageIds()) {
            GarageEntity garage = garageProcessor.getGarageEntityById(garageId);
            if (garage != null) {
                garages.add(garage);
            }
        }
        newCar.setGarages(garages);

        CarEntity savedCar = carRepository.save(newCar);

        List<GarageResponse> garageResponses = garages.stream()
            .map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            ))
            .collect(Collectors.toList());

        return new CarResponse(
            savedCar.getId(),
            savedCar.getMake(),
            savedCar.getModel(),
            savedCar.getProductionYear(),
            savedCar.getLicensePlate(),
            garageResponses
        );
    }

    protected CarEntity getCarEntityById(Long id) {
        return carRepository.findByIdAndIsActiveTrue(id).orElse(null);
    }

    public CarResponse getCarById(Long id) {
        CarEntity car = carRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (car == null) {
            return null;
        }

        List<GarageResponse> garageResponses = car.getGarages().stream()
            .map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            ))
            .collect(Collectors.toList());
        
        return new CarResponse(
            car.getId(),
            car.getMake(),
            car.getModel(),
            car.getProductionYear(),
            car.getLicensePlate(),
            garageResponses
        );
    }

    public CarResponse updateCar(Long id, CarRequest request) {
        CarEntity car = carRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (car == null) {
            return null;
        }

        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setProductionYear(request.getProductionYear());
        car.setLicensePlate(request.getLicensePlate());

        List<GarageEntity> garages = new ArrayList<>();
        for (Long garageId : request.getGarageIds()) {
            GarageEntity garage = garageProcessor.getGarageEntityById(garageId);
            if (garage != null) {
                garages.add(garage);
            }
        }
        car.setGarages(garages);

        CarEntity updatedCar = carRepository.save(car);

        List<GarageResponse> garageResponses = garages.stream()
            .map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            ))
            .collect(Collectors.toList());

        return new CarResponse(
            updatedCar.getId(),
            updatedCar.getMake(),
            updatedCar.getModel(),
            updatedCar.getProductionYear(),
            updatedCar.getLicensePlate(),
            garageResponses
        );
    }

    public CarResponse deleteCar(Long id) {
        CarEntity car = carRepository.findByIdAndIsActiveTrue(id).orElse(null);
        if (car == null) {
            return null;
        }

        car.setIsActive(false);
        
        CarEntity deletedCar = carRepository.save(car);

        List<GarageResponse> garageResponses = car.getGarages().stream()
            .map(garage -> new GarageResponse(
                garage.getId(),
                garage.getName(),
                garage.getLocation(),
                garage.getCity(),
                garage.getCapacity()
            ))
            .collect(Collectors.toList());

        return new CarResponse(
            deletedCar.getId(),
            deletedCar.getMake(),
            deletedCar.getModel(),
            deletedCar.getProductionYear(),
            deletedCar.getLicensePlate(),
            garageResponses
        );
    }
}
