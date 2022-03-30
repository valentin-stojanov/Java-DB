package softuni.exam.service.impl;

import org.springframework.stereotype.Service;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    public static final Path CAR_FILE_PATH = Path.of("src/main/resources/files/json/cars.json");
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(CAR_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importCars() throws IOException {
        return null;
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        return null;
    }
}
