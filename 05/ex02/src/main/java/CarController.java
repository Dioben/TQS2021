import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    @Autowired
    CarManagerService carManagerService;
    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(Car x){
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save(x);
        return new ResponseEntity<>(saved,status);
    }
    @GetMapping(path = "/cars", produces = "application/json")
    public List<Car> getAllCars(){return carManagerService.getAllCars();}

    @GetMapping(path = "/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws  ResourceNotFoundException{
        Car car = carManagerService.getCarDetails(carId).orElseThrow(() -> new ResourceNotFoundException("Car not found for ID "+carId));
        return ResponseEntity.ok().body(car);
    }
}
