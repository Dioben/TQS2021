import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CarManagerService {
    @Autowired
    CarRepository carRepository;

    public Car save(Car x){carRepository.save(x);
        return x;
    }
    public List<Car> getAllCars(){return carRepository.findAll();}
    public Optional<Car> getCarDetails(Long id){
        return Optional.of(carRepository.findByCarId(id));
        }
}
