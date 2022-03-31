package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarImportDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    public static final Path CAR_FILE_PATH = Path.of("src/main/resources/files/json/cars.json");
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          ModelMapper modelMapper,
                          Gson gson,
                          ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;

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
        CarImportDTO[] carImportDTO = this.gson.fromJson(readCarsFileContent(), CarImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (CarImportDTO carDTO : carImportDTO) {
            boolean isValidDTO = this.validationUtil.isValid(carDTO);
            if (!isValidDTO){
                result.append("Invalid car\n");
                continue;
            }
            Car car = this.modelMapper.map(carDTO, Car.class);
            this.carRepository.save(car);
            result.append(String.format("Successfully imported car - %s - %s\n",car.getMake(), car.getModel()));
        }
        return result.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        return null;
    }
}
